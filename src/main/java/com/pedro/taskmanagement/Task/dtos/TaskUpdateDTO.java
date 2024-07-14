package com.pedro.taskmanagement.Task.dtos;

import com.pedro.taskmanagement.Task.model.Task;

public class TaskUpdateDTO {

    private String title;
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
