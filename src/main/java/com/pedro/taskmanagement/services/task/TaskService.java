package com.pedro.taskmanagement.services.task;

import com.pedro.taskmanagement.domain.task.Task;
import com.pedro.taskmanagement.dto.task.TaskCreateDTO;
import com.pedro.taskmanagement.dto.task.TaskUpdateDTO;
import com.pedro.taskmanagement.exception.ObjectNotFoundException;
import com.pedro.taskmanagement.repositories.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    TaskRepository repository;

    public List<Task> listAllTasks() {
        return repository.findAll();
    }

    public Task listTaskById(UUID id) {
        Optional<Task> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Task Not Found"));
    }

    public Task createTask(TaskCreateDTO obj){
        Task t = new Task(null, obj.getTitle(), obj.getDescription(), null);
        return repository.save(t);
    }

    public Task updateTask(UUID id, TaskUpdateDTO obj) {
        Task task = repository.getReferenceById(id);
        task.setTitle(obj.getTitle());
        task.setDescription(obj.getDescription());
        return repository.save(task);
    }

    public void deleteTask(UUID id) {
        repository.deleteById(id);
    }
}
