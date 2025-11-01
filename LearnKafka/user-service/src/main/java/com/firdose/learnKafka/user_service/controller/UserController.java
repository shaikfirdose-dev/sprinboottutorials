package com.firdose.learnKafka.user_service.controller;

import com.firdose.learnKafka.user_service.dto.UserRequestDto;
import com.firdose.learnKafka.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    @Value("${spring.topic.name.user}")
    private String KAFKA_RANDOM_USER_TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        userService.createUser(userRequestDto);
        return ResponseEntity.ok("Message Queued");
    }

    @PostMapping("/users/{message}")
    public ResponseEntity<String> sayHello(@PathVariable String message) {
        kafkaTemplate.send(KAFKA_RANDOM_USER_TOPIC, message);
        log.info("User created");
        return ResponseEntity.ok("Message Queued");
    }
}
