package com.raserei.popugjira.tracker.service;


import com.raserei.popugjira.tracker.domain.*;
import com.raserei.popugjira.tracker.event.EventProducer;
import com.raserei.popugjira.tracker.event.TaskClosedEvent;
import com.raserei.popugjira.tracker.event.TaskCreatedEvent;
import com.raserei.popugjira.tracker.event.TaskReassignedEvent;
import com.raserei.popugjira.tracker.exception.NoEmployeesAvailable;
import com.raserei.popugjira.tracker.exception.TaskNotFoundException;
import com.raserei.popugjira.tracker.rest.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskReassignmentOrderingRepository taskReassignmentOrderingRepository;

    private final EventProducer eventProducer;

    public Task createTask(TaskDTO taskDTO) throws NoEmployeesAvailable {
        UserAccount assignee = userService.getRandomUserAccount();
        Task task = new Task(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                taskDTO.shortDescription(),
                taskDTO.longDescription(),
                assignee,
                TaskStatus.OPEN);

        taskRepository.save(task);
        eventProducer.sendTaskCreatedEvent(new TaskCreatedEvent(task.getPublicId(),
                task.getShortDescription(),
                task.getAssignee().getPublicId()));
        return task;
    }

    public void shuffleTasks() {
        taskReassignmentOrderingRepository.createOpenTaskReassignments();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reassignTask(TaskReassignmentOrdering order) throws NoEmployeesAvailable {
        Task task = order.getTask();
        if (task.getStatus().equals(TaskStatus.CLOSED)) {
            taskReassignmentOrderingRepository.delete(order);
            return;
        }
        task.setAssignee(userService.getRandomUserAccount());
        taskRepository.save(task);
        taskReassignmentOrderingRepository.delete(order);
        eventProducer.sendTaskReassignedEvent(new TaskReassignedEvent(task.getPublicId(),
                task.getShortDescription(),
                task.getAssignee().getPublicId()));
    }

    public void closeTask(String taskPublicId) throws TaskNotFoundException {
        Task task = taskRepository.findByPublicId(taskPublicId).orElseThrow(TaskNotFoundException::new);
        if (task.getStatus().equals(TaskStatus.OPEN)) {
            task.setStatus(TaskStatus.CLOSED);
            eventProducer.sendTaskClosedEvent(new TaskClosedEvent(taskPublicId, task.getAssignee().getPublicId()));
        }
    }


}
