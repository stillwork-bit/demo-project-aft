package org.tan.kafka.data;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tan.kafka.helper.Topic;

import java.util.HashSet;

@Slf4j
@Data
@Component
public class DataStoreKafka {

    @Autowired
    private Topic constants;

    private HashSet<String> CONSUMER = new HashSet<>();

    public HashSet<String> consumerHashSet(String message) {
        CONSUMER.add(message);
        return CONSUMER;
    }

    public void cleanDataFromTopic() {
        CONSUMER.clear();
    }

    public HashSet<String> returnDataFromTopic(String topic) {
        HashSet<String> hashSet = null;
        if (topic.equals(constants.CONSUMER_TOPIC)) {
            hashSet = CONSUMER;
        } else {
            log.error("===== Ошибка. Указан несуществующий топик  =====");
        }

        return hashSet;
    }
}
