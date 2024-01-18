package org.tan.kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.tan.kafka.data.DataStoreKafka;

import java.util.HashSet;

@Slf4j
@Primary
@CrossOrigin()
@RestController
@RequestMapping("/")
public class KafkaConsumerListener {

    @Autowired
    private DataStoreKafka dataStore;

    @KafkaListener(topics = "${spring.kafka.consumer.topics-title.consumer}", groupId = "${spring.kafka.groupId}")
    public void listenEventConsumer(String message) {
        try {
            log.info("===== Получено сообщение topic = {}, message = {} =====", "event", message);
            dataStore.consumerHashSet(message);
        } catch (KafkaException e) {
            log.info("===== Ошибка при прослушивании данных в топике =====");
        }
    }

    @GetMapping("/getDataFromKafkaTopic")
    public ResponseEntity<String> getDataFromKafkaTopic(@RequestParam String topic) {
        log.info("===== Старт команды для получения данных из топика : {} =====", topic);

        HashSet<String> allData = null;
        try {
            allData = dataStore.returnDataFromTopic(topic);
        } catch (Exception e) {
            log.error("===== Ошибка при извлечении данных из коллекции =====");
            e.printStackTrace();
        }
        return allData.size() > 0 ? ResponseEntity.ok(allData.toString()) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("===== Ошибка. Пустая коллекция =====");
    }

    @GetMapping("/deleteDataKafkaList")
    public ResponseEntity<String> deleteDataKafkaList() {
        dataStore.cleanDataFromTopic();
        log.info("===== Данные в коллекции удалены успешно =====");
        return ResponseEntity.ok("===== Данные в коллекции удалены успешно =====");
    }
}