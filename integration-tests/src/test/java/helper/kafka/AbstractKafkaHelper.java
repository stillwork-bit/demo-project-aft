package helper.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import helper.AllureHelper;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import static helper.kafka.KafkaConsumerCreator.createConsumer;
import static helper.kafka.KafkaProducerCreator.createProducer;
import static java.time.Duration.ofMillis;

@Slf4j
public abstract class AbstractKafkaHelper<T> {
    private final String server;
    private final String topic;
    private final ObjectMapper om;
    private final Class<T> responseClass;
    private final String uniqueRecordId;

    private ConsumerRecords<String, String> consumerRecords;
    private List<Record<T>> records;

    protected AbstractKafkaHelper(@NonNull String server, @NonNull String topic, String uniqueRecordId, @NonNull ObjectMapper om, Class<T> responseClass) {
        this.server = server;
        this.topic = topic;
        this.om = om;
        this.responseClass = responseClass;
        this.uniqueRecordId = Objects.isNull(uniqueRecordId) ? "eventId" : uniqueRecordId;
    }

    public String addMsg(Object msg) {
        return sendMsg(msg, null);
    }

    public String addMsg(Object msg, Map<String, String> headers) {
        return sendMsg(msg, headers);
    }

    @SneakyThrows
    @Step("Отправить сообщение в топик %s с eventId = %s")
    private String sendMsg(Object msg, @Nullable Map<String, String> headers) {
        final String eventId = UUID.randomUUID().toString();
        final String message = msg instanceof String ? (String) msg : om.writeValueAsString(msg);

        List<Parameter> parameters = new ArrayList<>();

        try (Producer<String, String> producer = createProducer(server)) {
            try {

                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, message);

                if (Objects.nonNull(headers)) {
                    for (var entry : headers.entrySet()) {
                        producerRecord.headers().add(entry.getKey(), entry.getValue().getBytes());

                        parameters.add(new Parameter()
                                .setName("header")
                                .setValue(entry.getKey() + ": " + entry.getValue())
                        );
                    }
                }

                producerRecord.headers().add(uniqueRecordId, eventId.getBytes());

                producer.send(producerRecord, (metadata, exception) -> {
                    if (Objects.nonNull(exception)) {
                        log.error("Send msg exception: {}", exception.getMessage());
                    }
                });

                log.info("Message {} was send to topic \"{}\"", message, topic);
            } finally {
                producer.flush();
            }
        } finally {
            AllureHelper.getAllureHelper()
                    .formatStepName(topic, eventId)
                    .addAttachment(msg, "sent message",
                            om instanceof JsonMapper ? AllureHelper.ContentType.JSON : AllureHelper.ContentType.XML)
                    .addParameters(parameters);
        }

        return eventId;
    }

    @Step("Получить все сообщения из топика %s")
    @Deprecated
    public List<T> getAllMsg() {
        final List<Record<T>> msg = getRecords(() ->
                    consumerRecords.forEach(rec -> records.add(convert(rec)))
        );

        if (msg != null) {
            return msg.stream().map(Record::getValue).collect(Collectors.toList());
        } else return Collections.emptyList();
    }

    @Step("Получить из топика %s запись с текстом = {0}")
    public Record<T> getRecordByTexts(String... texts) {
        final List<Record<T>> record = getRecords(() -> consumerRecords.forEach(rec -> {
                    boolean isContainsAll = Arrays.stream(texts).allMatch(text->rec.value().contains(text));

                    if (isContainsAll && texts.length != 0) {
                        records.add(convert(rec));
                    }
                })
        );
        return returnFirstIfNotNull(record);

    }

    @Step("Получить из топика %s запись с key = {0}")
    public Record<T> getRecordByKey(String key) {
        final List<Record<T>> record = getRecords(() -> consumerRecords.forEach(rec -> {
                    if (rec.key().equals(key)) {
                        records.add(convert(rec));
                    }
                })
        );

        return returnFirstIfNotNull(record);
    }

    private void addAllureAttachment(Record<T> record) {
        log.info("Message {} with headers {} was received from topic \"{}\"", record.getValue(), record.getHeaders(), topic);

        AllureHelper.getAllureHelper()
                .addAttachment(record.getValue(), "received messages", getContentType());
    }

    private Record<T> returnFirstIfNotNull(List<Record<T>> records) {
        if (Objects.nonNull(records) && !records.isEmpty()) {
            return records.get(0);
        } else return null;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Record<T> convert(ConsumerRecord<String, String> consumerRecord) {
        Record<T> record = new Record<>();
        record.setKey(consumerRecord.key());
        record.setHeaders(consumerRecord.headers());

        if (responseClass == null || responseClass.equals(String.class)) {
            record.setValue((T) consumerRecord.value());
        } else {
            record.setValue(om.readValue(consumerRecord.value(), responseClass));
        }

        addAllureAttachment(record);

        return record;
    }

    @SneakyThrows
    private synchronized List<Record<T>> getRecords(Runnable filter) {
        records = new ArrayList<>();

        try (Consumer<String, String> consumer = createConsumer(server, topic)) {
            final long timeout = 10;
            final int pollIntervalMs = 100;

            final var giveUp = (timeout * 1000) / pollIntervalMs;

            var noRecordsCount = 0;

            while (true) {
                final ConsumerRecords<String, String> consumerRecords =
                        consumer.poll(ofMillis(pollIntervalMs));

                if (consumer.assignment().isEmpty()) continue;

                if (consumerRecords.isEmpty()) {
                    noRecordsCount++;

                    if (noRecordsCount > giveUp) {
                        break;
                    } else {
                        continue;
                    }
                }

                this.consumerRecords = consumerRecords;

                filter.run();

                consumer.commitAsync((offsets, exception) -> {
                    if (Objects.nonNull(exception)) {
                        log.error("Async commit exception: {}", exception.getMessage());
                    }
                });

                if (records.size() != 0) {
                    return records;
                }
            }
        } finally {
            AllureHelper.getAllureHelper()
                    .formatStepName(topic);
        }

        return null;
    }

    protected abstract AllureHelper.ContentType getContentType();
}
