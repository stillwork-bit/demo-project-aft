package helper.kafka;

import lombok.experimental.UtilityClass;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

import static helper.Constants.*;
import static helper.kafka.KafkaSsl.sslEnabled;

@UtilityClass
public class KafkaConsumerCreator {

    public static Consumer<String, String> createConsumer(String server, String topic) {
        final var properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,
                GROUP_ID_CONFIG + Thread.currentThread().getId());
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
                MAX_POLL_INTERVAL_MS_CONFIG);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                MAX_POLL_RECORDS_CONFIG);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                AUTO_OFFSET_RESET_CONFIG);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

        sslEnabled(properties);

        final Consumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(Collections.singletonList(topic));

        return consumer;
    }
}
