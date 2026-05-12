package com.nanashe.backend.kafka.consumer;

import com.nanashe.backend.kafka.event.KafkaAlternativesEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlternativesKafkaConsumer {

    @KafkaListener(topics = "alternatives", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(KafkaAlternativesEvent message) {
        log.info("Received Kafka message: {} aliases, {} alternatives",
                message.aliases() != null ? message.aliases().size() : 0,
                message.alternatives() != null ? message.alternatives().size() : 0);
    }
}