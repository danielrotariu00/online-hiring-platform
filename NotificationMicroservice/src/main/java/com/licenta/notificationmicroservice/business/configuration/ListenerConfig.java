package com.licenta.notificationmicroservice.business.configuration;

import com.licenta.notificationmicroservice.business.constants.KafkaConstants;
import com.licenta.notificationmicroservice.business.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ListenerConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Notification> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, Notification> consumerFactory() {
        Map<String, Object> configurations = new HashMap<>();
        JsonDeserializer<Notification> jsonDeserializer = new JsonDeserializer<>(Notification.class);
        jsonDeserializer.setRemoveTypeHeaders(false);
        jsonDeserializer.setUseTypeMapperForKey(true);
        jsonDeserializer.addTrustedPackages("*");

        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, jsonDeserializer);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");


        return new DefaultKafkaConsumerFactory<>(
                configurations,
                new StringDeserializer(),
                jsonDeserializer
        );
    }
}
