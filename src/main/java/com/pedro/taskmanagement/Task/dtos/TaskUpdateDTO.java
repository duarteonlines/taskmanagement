package com.pedro.taskmanagement.Task.dtos;

import com.pedro.taskmanagement.Task.model.Task;
import jakarta.validation.constraints.NotBlank;

public class TaskUpdateDTO {

    @NotBlank(message = " is null")
    private String title;
    @NotBlank(message = " is null")
    private String description;

    public TaskUpdateDTO(){

    }

    public TaskUpdateDTO(Task task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
