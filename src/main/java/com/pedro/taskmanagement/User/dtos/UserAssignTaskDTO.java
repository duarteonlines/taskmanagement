package com.pedro.taskmanagement.User.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserAssignTaskDTO {


    @NotBlank(message = "Id(STRING) is null")
    private String user;
    @NotBlank(message = "Id(STRING) is null")
    private String Task;

    public UserAssignTaskDTO(){
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }
}
