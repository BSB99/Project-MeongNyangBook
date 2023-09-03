/*package com.example.meongnyangbook.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceMessage(String topic, String payload) {
        //log.info("Producer TOPIC : " + topic);
        //log.info("Producer PAYLOAD : " + payload);
        kafkaTemplate.send(topic, payload);
    }
}*/
