package com.pedro.taskmanagement.Task.service;

import com.pedro.taskmanagement.Exceptions.exceptions.AlreadyExistsException;
import com.pedro.taskmanagement.Exceptions.exceptions.ObjectNotFoundException;
import com.pedro.taskmanagement.Task.dtos.TaskCreateDTO;
import com.pedro.taskmanagement.Task.dtos.TaskUpdateDTO;
import com.pedro.taskmanagement.Task.model.Task;
import com.pedro.taskmanagement.Task.repository.TaskRepository;
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
        checkExistenceUniqueData(obj);
        Task task = new Task();
        taskCreateData(task, obj);
        return repository.save(task);
    }

    public void updateTask(UUID id, TaskUpdateDTO obj) {
        Optional<Task> existsTask = repository.findById(id);
        validateExistenceTask(existsTask);
        Task task = existsTask.get();
        taskUpdateData(task, obj);
        repository.save(task);
    }

    public void deleteTask(UUID id) {
        Optional<Task> existsTask = repository.findById(id);
        validateExistenceTask(existsTask);
        repository.deleteById(id);
    }

    private void checkExistenceUniqueData(TaskCreateDTO obj) {
        Optional<Task> task = repository.findByTitle(obj.getTitle());
        if (task.isPresent()){
            throw new AlreadyExistsException("Task title already exists");
        }
    }
    private void validateExistenceTask(Optional<Task> task) {
        if(task.isEmpty()){
            throw new ObjectNotFoundException("Task not found");
        }
    }

    private void taskUpdateData(Task task, TaskUpdateDTO obj){
        task.setTitle(obj.getTitle());
        task.setDescription(obj.getDescription());
    }

    private void taskCreateData(Task task, TaskCreateDTO obj){
        task.setTitle(obj.getTitle());
        task.setDescription(obj.getDescription());
        task.setUser(null);
    }
}
