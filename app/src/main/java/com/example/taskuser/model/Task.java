package com.example.taskuser.model;

import java.io.Serializable;
import java.util.UUID;

public class Task  implements Serializable {
    private UUID taskID;



    private UUID userIdForeign;
    private  String title;
    private String description;
    private  TaskState state;
    private String dateTask;
    private String timeTask;

    public UUID getUserIdForeign() {
        return userIdForeign;
    }

    public void setUserIdForeign(UUID userIdForeign) {
        this.userIdForeign = userIdForeign;
    }
    public UUID getTaskID() {
        return taskID;
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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getDateTask() {
        return dateTask;
    }

    public void setDateTask(String dateTask) {
        this.dateTask = dateTask;
    }

    public String getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(String timeTask) {
        this.timeTask = timeTask;
    }

    public Task() {
        this.taskID = UUID.randomUUID();
    }
}
