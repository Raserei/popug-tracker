package com.raserei.popugjira.tracker.event;

import com.raserei.popugjira.tracker.event.user.UserCreatedEvent;
import com.raserei.popugjira.tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final UserService userService;

    @KafkaListener(topics = "${kafka.topic.user.created}", containerFactory = "userCreatedListener")
    public void processUserCreatedEvent(UserCreatedEvent userCreatedEvent) {
        userService.createUser(userCreatedEvent.publicId(), userCreatedEvent.roles());
    }

}
