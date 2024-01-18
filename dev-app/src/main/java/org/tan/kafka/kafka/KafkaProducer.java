package org.tan.kafka.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.tan.data.dto.kafka.KafkaSenderDTO;
import org.tan.kafka.helper.Helper;


@Primary
@CrossOrigin()
@RestController
@RequestMapping("/")
@Slf4j
@Component
public class KafkaProducer {

    @Autowired
    private Helper helper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //https://www.baeldung.com/spring-kafka
    @PostMapping(value = "/sendMessageToKafkaByFile")
    public void sendMessageToKafkaByFile(@RequestBody KafkaSenderDTO ks) {
        log.info("===== Отравка сообщения в KAFKA topic {} \n и сообщения {}", ks.getTopic(), ks.getMessage());
        kafkaTemplate.send(ks.getTopic(), helper.readFile(ks.getFile()));
    }

    @PostMapping(value = "/sendMessageToKafkaByString")
    public void sendMessageToKafkaByStringtest(@RequestBody KafkaSenderDTO ks) {
        log.info("===== Отравка сообщения в KAFKA topic {} \n и сообщения {}", ks.getTopic(), ks.getMessage());
        try {
            kafkaTemplate.send(ks.getTopic(), ks.getMessage());
        } catch (Exception e) {
            log.error("===== Ошибка отправки данных в KAFKA topic {} \n и сообщения {}", ks.getTopic(), ks.getMessage());
            e.printStackTrace();
        }
    }
}
