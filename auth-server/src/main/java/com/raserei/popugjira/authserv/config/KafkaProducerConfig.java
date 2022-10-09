package com.raserei.popugjira.authserv.config;

import com.raserei.popugjira.authserv.event.UserCreatedEvent;
import com.raserei.popugjira.authserv.event.UserDeletedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.producer.server}")
    private String server;

    @Value("${kafka.producer.group}")
    private String group;

    @Bean
    public <T>ProducerFactory<String, T> producerFactory(JsonSerializer<T> valueSerializer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        valueSerializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), valueSerializer);
    }

    @Bean
    public KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(new JsonSerializer<>()));
    }

    @Bean
    public KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(new JsonSerializer<>()));
    }
}

