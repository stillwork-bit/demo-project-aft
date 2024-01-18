package org.tan.kafka.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Topic {

    @Value("${spring.kafka.consumer.topics-title.consumer:}")
    public String CONSUMER_TOPIC;

}
