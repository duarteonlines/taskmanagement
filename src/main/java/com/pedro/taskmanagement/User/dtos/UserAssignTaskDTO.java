package com.pedro.taskmanagement.User.dtos;

import java.util.UUID;

public class UserAssignTaskDTO {

    private UUID user;
    private UUID Task;

    public UserAssignTaskDTO(){
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public UUID getTask() {
        return Task;
    }

    public void setTask(UUID task) {
        Task = task;
    }
}
