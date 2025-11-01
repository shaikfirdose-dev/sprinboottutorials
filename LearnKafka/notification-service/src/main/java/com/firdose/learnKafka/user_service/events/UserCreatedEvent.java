package com.firdose.learnKafka.user_service.events;

import lombok.Data;

@Data
public class UserCreatedEvent {

    private Long id;
    private String name;
    private String email;
}
