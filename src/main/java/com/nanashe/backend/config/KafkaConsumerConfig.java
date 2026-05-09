package com.nanashe.backend.config;

import com.nanashe.backend.dto.kafka.KafkaAlternativesMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, KafkaAlternativesMessageDto> alternativesConsumerFactory() {
        JsonDeserializer<KafkaAlternativesMessageDto> deserializer =
                new JsonDeserializer<>(KafkaAlternativesMessageDto.class, false);

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
    public ConcurrentKafkaListenerContainerFactory<String, KafkaAlternativesMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaAlternativesMessageDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(alternativesConsumerFactory());
        return factory;
    }
}
