package api.kafka;

import helper.kafka.JsonKafkaHelper;
import helper.kafka.Record;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.tan.data.dto.pet.TagDTO;

import java.util.*;

import static helper.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("Epic Kafka")
@Feature("Feature Kafka Tests")
@Story("Write and Read kafka topic")
@DisplayName("Тестирование записи и чтение сообщения из топика kafka")
public class KafkaTests {


    private static JsonKafkaHelper<TagDTO> printRequestTopic = new JsonKafkaHelper<>(KAFKA_SERVER, PRODUCER_TOPIC, TagDTO.class);
    private static JsonKafkaHelper<TagDTO> printResponseTopic = new JsonKafkaHelper<>(KAFKA_SERVER, CONSUMER_TOPIC, TagDTO.class);

    /**
     * Пример ниже описан для примера для взаимодействия с kafka в тестах.
     * При внедрении подхода автоматизации по указанному паттерну требуется выделять step'ы по примеру в apiTests.restTestVariant1.steps.PetsStep
     */

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("KafkaTest")})
    @DisplayName("Тестирование запроса Delete c удалением Pet с заполненем Headers в записываем сообщении")
    void exampleWriteAndReadDataFromTopicWithHeaders() {
        int id = (int) (Math.random() * 50 + 1);
        TagDTO request = TagDTO.builder()
                .id(id)
                .name("testName" + id)
                .build();

        printRequestTopic.addMsg(request, Map.of("headerKey", "headerValue"));
        /**
         * Пример поиска сообщения по getRecordByTexts
         */
        Record<TagDTO> findRecordByText = printResponseTopic.getRecordByTexts(request.getName());
        assertEquals(request.getId(), findRecordByText.getValue().getId());
        assertEquals(request.getName(), findRecordByText.getValue().getName());

        /** Метод с поиском данных можно доработать под свои условия. Пример:  -->
         *Record<TagDTO> findRecordByKey = printResponseTopic.getRecordByKey(3);
         *assertEquals(request.getId(), findRecordByKey.getValue().getId());
         *assertEquals(request.getName(), findRecordByKey.getValue().getName());
         * */
    }

    @Test
    @Tags(value = {@Tag("RegressAPI"), @Tag("RegressALL"), @Tag("KafkaTest")})
    @DisplayName("Тестирование запроса Delete c удалением Pet без заполнения Headers в записываем сообщении")
    void exampleWriteAndReadDataFromTopicWithoutHeaders() {
        int id = (int) (Math.random() * 50 + 1);
        TagDTO request = TagDTO.builder()
                .id(id)
                .name("testName" + id)
                .build();

        printRequestTopic.addMsg(request);
        /**
         * Пример поиска сообщения по getRecordByTexts
         */
        Record<TagDTO> findRecordByText = printResponseTopic.getRecordByTexts(request.getName());
        assertEquals(request.getId(), findRecordByText.getValue().getId());
        assertEquals(request.getName(), findRecordByText.getValue().getName());

        /** Метод с поиском данных можно доработать под свои условия. Пример:  -->
         *Record<TagDTO> findRecordByKey = printResponseTopic.getRecordByKey(3);
         *assertEquals(request.getId(), findRecordByKey.getValue().getId());
         *assertEquals(request.getName(), findRecordByKey.getValue().getName());
         * */
    }
}
