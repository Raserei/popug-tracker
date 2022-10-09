package com.raserei.popugjira.tracker.config;

import com.raserei.popugjira.tracker.event.user.UserCreatedEvent;
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


import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.server}")
    private String server;

    @Value("${kafka.consumer.group}")
    private String group;

    @Bean
    public <T>ConsumerFactory<String, T> consumerFactory(JsonDeserializer<T> jsonDeserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        jsonDeserializer.ignoreTypeHeaders();
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> userCreatedListener() {
        ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(new JsonDeserializer<>(UserCreatedEvent.class)));
        return factory;
    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent> userDeletedListener() {
//        ConcurrentKafkaListenerContainerFactory<String, UserCreatedEvent>
//                factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory(new JsonDeserializer<>(UserD.class)));
//        return factory;
//    }
}