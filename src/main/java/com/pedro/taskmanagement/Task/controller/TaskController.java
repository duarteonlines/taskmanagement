package com.pedro.taskmanagement.Task.controller;

import com.pedro.taskmanagement.Task.dtos.TaskCreateDTO;
import com.pedro.taskmanagement.Task.dtos.TaskListDTO;
import com.pedro.taskmanagement.Task.dtos.TaskUpdateDTO;
import com.pedro.taskmanagement.Task.model.Task;
import com.pedro.taskmanagement.Task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tasks", produces = {"application/json"})
public class TaskController {

    @Autowired
    TaskService service;

    @Operation(summary = "Search for all registered tasks", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List all tasks"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not Authorized"),
            @ApiResponse(responseCode = "500", description = "Internal Sever Error")
    })
    @GetMapping
    public ResponseEntity<List<TaskListDTO>> listAllTask() {
        List<TaskListDTO> list = service.listAllTasks().stream().map(x -> new TaskListDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
     }

     @Operation(summary = "Search for task per id", method = "GET")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "List task per id"),
             @ApiResponse(responseCode = "400", description = "Invalid request params"),
             @ApiResponse(responseCode = "401", description = "Not Authenticated"),
             @ApiResponse(responseCode = "403", description = "Not Authorized"),
             @ApiResponse(responseCode = "500", description = "Internal Sever Error")
     })
     @GetMapping("/{id}")
     public ResponseEntity<TaskListDTO> listById(@PathVariable UUID id) {
        TaskListDTO task = new TaskListDTO(service.listTaskById(id));
        return ResponseEntity.ok().body(task);
     }

     @Operation(summary = "Create a new task", method = "POST")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "201", description = "Task created successfully"),
             @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
             @ApiResponse(responseCode = "401", description = "Not Authenticated"),
             @ApiResponse(responseCode = "403", description = "Not authorized"),
             @ApiResponse(responseCode = "422", description = "Invalid request data"),
             @ApiResponse(responseCode = "500", description = "Internal Server Error")

     })
     @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody @Valid TaskCreateDTO obj) {
        Task task  = service.createTask(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(task.getId()).toUri();
        return ResponseEntity.created(uri).build();
     }

     @Operation(summary = "Update a task", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
    @PutMapping(value = "/{id}" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTask(@PathVariable UUID id,@RequestBody @Valid TaskUpdateDTO obj) {
        service.updateTask(id, obj);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a task", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "401", description = "Not Authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "422", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")

    })
     @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
     }
}
