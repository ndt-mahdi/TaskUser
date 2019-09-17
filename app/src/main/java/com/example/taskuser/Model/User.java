package com.example.taskuser.Model;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class User {
    private UUID userId;
    private String userName;
    private int password;
    private List<Task> taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }


   public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getPassword() == user.getPassword() &&
                getUserName().equals(user.getUserName());
    }


////////// Insert , Edit , Delete task's method

    public void insertTask(Task task)
    {
        taskList.add(task);
    }

    public void editTask(Task task) throws Exception {
        Task newTask = getTask(task.getTaskID());
        if (newTask == null)
            throw new Exception("This Task does not exist!!!");

        newTask.setTitle(task.getTitle());
        newTask.setDateTask(task.getDateTask());
        newTask.setDescription(task.getDescription());
        newTask.setState(task.getState());
        newTask.setTimeTask(task.getTimeTask());
        newTask.setIconState(task.getIconState());
    }

    public void deleteTask(Task Task) throws Exception {
        Task oldTask = getTask(Task.getTaskID());
        if (oldTask == null)
            throw new Exception("This Task does not exist!!!");

        taskList.remove(oldTask);
    }
    public int getPositionTask(UUID uuid) {
        return taskList.indexOf(getTask(uuid));
    }

    public Task getTask(UUID uuid) {
        for (Task task : taskList)
            if (task.getTaskID().equals(uuid))
                return task;

        return null;
    }
}
