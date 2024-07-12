package com.pedro.taskmanagement.services.task;

import com.pedro.taskmanagement.domain.task.Task;
import com.pedro.taskmanagement.exception.ObjectNotFoundException;
import com.pedro.taskmanagement.repositories.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskService {

    @Autowired
    TaskRepository repository;

    private List<Task> listAllTaks() {
        return repository.findAll();
    }

    private Task listTaskById(UUID id) {
        Optional<Task> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Task Not Found"));
    }
}
