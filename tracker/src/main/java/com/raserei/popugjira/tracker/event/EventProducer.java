package com.raserei.popugjira.tracker.event;

public interface EventProducer {
    void sendTaskCreatedEvent(TaskCreatedEvent message);

    void sendTaskReassignedEvent (TaskReassignedEvent event);

    void sendTaskClosedEvent (TaskClosedEvent event);
}
