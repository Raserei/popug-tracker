package com.raserei.popugjira.authserv.event;

public interface EventProducer {
    void sendUserCreatedEvent(UserCreatedEvent userCreatedEvent);

    void sendUserDeletedEvent(UserDeletedEvent userDeletedEvent);
}
