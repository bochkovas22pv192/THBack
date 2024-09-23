package com.example.THBack.services;



import com.example.THBack.State;
import com.example.THBack.models.Task;
import com.example.THBack.repository.TaskRepository;
import com.example.THBack.exceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository repository;

    private void checkForExceptions(Task newTask){
        if(newTask.getName().isEmpty()){
            throw new TaskNameEmptyException();
        }

        if(newTask.getName().isBlank()){
            throw new TaskNameWhitespaceException();
        }

        if(newTask.getDescription().isEmpty()){
            throw new TaskDescriptionEmptyException();
        }

        if(newTask.getDescription().isBlank()){
            throw new TaskDescriptionWhitespaceException();
        }

        if(newTask.getDate().isBefore(LocalDate.now())){
            throw new DateExecuteMinException();
        }

        if(newTask.getEmailExecutor().isEmpty()){
            throw new TaskEmailExecutorEmptyException();
        }
    }


    public Optional<Task> getTasksById(Long id){
        return repository.findById(id);
    }

    public List<Task> getTasksAll(){
        return repository.findAll();
    }

    public Task postTask(Task newTask){
        checkForExceptions(newTask);

        newTask.setTimeUpdate(LocalDateTime.now());
        newTask.setState(State.NEW);
        newTask.setTimeCreate(LocalDateTime.now());
        return repository.save(newTask);

    }

    public Task putTask(Task newTask, Long id){
        checkForExceptions(newTask);

        newTask.setTimeUpdate(LocalDateTime.now());
        return repository.findById(id)
                .map(task -> {
                    task.setEmail(newTask.getEmail());
                    task.setName(newTask.getName());
                    task.setDate(newTask.getDate());
                    task.setEmailExecutor(newTask.getEmailExecutor());
                    task.setDescription(newTask.getDescription());
                    task.setPriority(newTask.getPriority());
                    return repository.save(task);
                })
                .orElseThrow(() -> new NoTaskException());
    }
    public void deleteTask(Long id){
        repository.deleteById(id);
    }
}
