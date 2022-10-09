package com.raserei.popugjira.authserv.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer{

    @Value("${kafka.topic.user.created}")
    private String userCreatedTopic;

    @Value("${kafka.topic.user.deleted}")
    private String userDeletedTopic;

    private final KafkaTemplate<String, UserCreatedEvent> userCreatedEventKafkaTemplate;

    private final KafkaTemplate<String, UserDeletedEvent> userDeletedEventKafkaTemplate;

    @Override
    public void sendUserCreatedEvent(UserCreatedEvent message) {
        ListenableFuture<SendResult<String, UserCreatedEvent>> future =
                userCreatedEventKafkaTemplate.send(userCreatedTopic, message);
        getCallback(message, future);
    }

    @Override
    public void sendUserDeletedEvent(UserDeletedEvent message) {
        ListenableFuture<SendResult<String, UserDeletedEvent>> future =
                userDeletedEventKafkaTemplate.send(userDeletedTopic, message);
        getCallback(message, future);
    }

    private <K,V> void getCallback(V message, ListenableFuture<SendResult<K, V>> future) {
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<K, V> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
