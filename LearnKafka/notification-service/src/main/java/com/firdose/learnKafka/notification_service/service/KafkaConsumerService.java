package com.firdose.learnKafka.notification_service.service;

import com.firdose.learnKafka.user_service.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "user-created-topic", groupId = "notification-service")
    public void handleUserCreatedMessage(UserCreatedEvent userCreatedEvent) {
        log.info("Notification Service received User Created Event: {}", userCreatedEvent);
    }

    @KafkaListener(topics = "user-random-topic", groupId = "notification-service")
    public void consumeMessage1(String message) {
        log.info("Notification Service received message1: {}", message);
    }

    @KafkaListener(topics = "user-random-topic", groupId = "notification-service")
    public void consumeMessage2(String message) {
        log.info("Notification Service received message2: {}", message);
    }

    @KafkaListener(topics = "user-random-topic", groupId = "notification-service")
    public void consumeMessage3(String message) {
        log.info("Notification Service received message3: {}", message);
    }
}
