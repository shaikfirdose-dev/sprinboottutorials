package com.firdose.learnKafka.user_service.service;

import com.firdose.learnKafka.user_service.dto.UserRequestDto;
import com.firdose.learnKafka.user_service.entity.User;
import com.firdose.learnKafka.user_service.events.UserCreatedEvent;
import com.firdose.learnKafka.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${spring.topic.user.created}")
    private String KAFKA_USER_CREATED_TOPIC;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public void createUser(UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        User savedUser = userRepository.save(user);
        UserCreatedEvent userCreatedEvent = modelMapper.map(savedUser, UserCreatedEvent.class);
        kafkaTemplate.send(KAFKA_USER_CREATED_TOPIC, savedUser.getId(), userCreatedEvent);
    }
}
