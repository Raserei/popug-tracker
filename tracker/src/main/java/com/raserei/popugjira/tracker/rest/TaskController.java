package com.raserei.popugjira.tracker.rest;

import com.raserei.popugjira.tracker.domain.TaskStatus;
import com.raserei.popugjira.tracker.exception.NoEmployeesAvailable;
import com.raserei.popugjira.tracker.exception.TaskNotFoundException;
import com.raserei.popugjira.tracker.rest.dto.TaskDTO;
import com.raserei.popugjira.tracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping(path = "/tasks")
    public void getTasks(){

    }

    @PostMapping(path="/tasks")
    public void createTask(TaskDTO taskDTO) throws NoEmployeesAvailable {
        taskService.createTask(taskDTO);
    }


    @PostMapping(path="/management/tasks/shuffle")
    public void shuffleTasks(){
        taskService.shuffleTasks();
    }


    @PutMapping(path="/tasks/{taskId}")
    public void updateTask(@RequestBody TaskDTO taskDTO, @PathVariable(name = "taskId") String taskPublicId) throws TaskNotFoundException {
        if (taskDTO.status().equals(TaskStatus.CLOSED))
            taskService.closeTask(taskPublicId);
        else throw new IllegalArgumentException();
    }
}
