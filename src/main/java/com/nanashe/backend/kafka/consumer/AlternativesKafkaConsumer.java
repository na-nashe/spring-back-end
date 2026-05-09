package com.nanashe.backend.kafka;

import com.nanashe.backend.dto.kafka.KafkaAlternativesMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlternativesKafkaConsumer {

    @KafkaListener(topics = "alternatives", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(KafkaAlternativesMessageDto message) {
        log.info("Received Kafka message: {} aliases, {} alternatives",
                message.aliases() != null ? message.aliases().size() : 0,
                message.alternatives() != null ? message.alternatives().size() : 0);
    }
}
