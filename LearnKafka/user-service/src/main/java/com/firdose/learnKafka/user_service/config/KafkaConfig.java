package com.firdose.learnKafka.user_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${spring.topic.name.user}")
    private String KAFKA_RANDOM_USER_TOPIC;

    @Bean
    public NewTopic createTopic(){
        return new NewTopic(KAFKA_RANDOM_USER_TOPIC, 3, (short) 1);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
