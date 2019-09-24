package com.example.taskuser.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Task {
    private UUID taskID;
    //private UUID userIdForeign;
    private  String title;
    private String description;
    private  TaskState state;
    private int iconState;
    private Date dateTask;
    private Time timeTask;

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

    public int getIconState() {
        return iconState;
    }

    public void setIconState(int iconState) {
        this.iconState = iconState;
    }

    public Date getDateTask() {
        return dateTask;
    }

    public void setDateTask(Date dateTask) {
        this.dateTask = dateTask;
    }

    public Time getTimeTask() {
        return timeTask;
    }

    public void setTimeTask(Time timeTask) {
        this.timeTask = timeTask;
    }

    public Task() {
        this.taskID = UUID.randomUUID();
    }
}
