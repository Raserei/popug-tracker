package com.raserei.popugjira.tracker.event;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@RequiredArgsConstructor
public class EventProducerImpl implements EventProducer {

    @Value("${kafka.topic.task.created}")
    private String taskCreatedTopic;

    @Value("${kafka.topic.task.closed}")
    private String taskClosedTopic;

    @Value("${kafka.topic.task.reassigned}")
    private String taskReassignedTopic;

    private final KafkaTemplate<String, TaskCreatedEvent> taskCreatedEventKafkaTemplate;

    private final KafkaTemplate<String, TaskReassignedEvent> taskReassignedEventKafkaTemplate;

    private final KafkaTemplate<String, TaskClosedEvent> taskClosedEventKafkaTemplate;

    @Override
    public void sendTaskCreatedEvent(TaskCreatedEvent message) {
        ListenableFuture<SendResult<String, TaskCreatedEvent>> future =
                taskCreatedEventKafkaTemplate.send(taskCreatedTopic, message);
        getCallback(message, future);
    }

    @Override
    public void sendTaskReassignedEvent(TaskReassignedEvent event) {
        ListenableFuture<SendResult<String, TaskReassignedEvent>> future =
                taskReassignedEventKafkaTemplate.send(taskCreatedTopic, event);
        getCallback(event, future);
    }

    @Override
    public void sendTaskClosedEvent(TaskClosedEvent event) {
        ListenableFuture<SendResult<String, TaskClosedEvent>> future =
                taskClosedEventKafkaTemplate.send(taskCreatedTopic, event);
        getCallback(event, future);
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
