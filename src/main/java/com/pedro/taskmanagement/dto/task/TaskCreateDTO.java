package com.pedro.taskmanagement.dto.task;

import com.pedro.taskmanagement.domain.task.Task;

public class TaskCreateDTO {

    private String title;
    private String description;

    public TaskCreateDTO(){
    }

    public TaskCreateDTO(Task task) {
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
