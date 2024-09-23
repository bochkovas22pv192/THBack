package com.example.THBack.controllers;

import com.example.THBack.models.Task;
import com.example.THBack.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;




@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    @GetMapping("/{id}")
    Optional<Task> tasksById(@PathVariable Long id) {
        return service.getTasksById(id);
    }

    @GetMapping()
    List<Task> tasksAll() {
        return service.getTasksAll();
    }

    @PostMapping("/")
    Task createEmployee(@RequestBody Task newTask) {
        return service.postTask(newTask);
    }

    @PutMapping("/{id}")
    Task updateEmployee(@PathVariable Long id, @RequestBody Task newTask) {
        return service.putTask(newTask, id);
    }

    @DeleteMapping("/{id}")
    void deleteEmployee(@PathVariable Long id) {
        service.deleteTask(id);
    }
}
