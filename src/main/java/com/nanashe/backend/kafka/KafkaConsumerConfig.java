package com.nanashe.backend.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nanashe.backend.kafka.event.KafkaAlternativesEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, KafkaAlternativesEvent> consumerFactory(ObjectMapper objectMapper) {
        JsonDeserializer<KafkaAlternativesEvent> deserializer =
                new JsonDeserializer<>(KafkaAlternativesEvent.class, objectMapper);
        deserializer.setUseTypeHeaders(false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        ConsumerConfig.GROUP_ID_CONFIG, groupId,
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset
                ),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaAlternativesEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, KafkaAlternativesEvent> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, KafkaAlternativesEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
