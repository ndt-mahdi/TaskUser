package com.example.taskuser.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository ourInstance;
    private List<User> userList;
    private List<Task> taskList;


    public static Repository getInstance() {
        if(ourInstance==null)
            ourInstance=new Repository();
        return ourInstance;
    }
    public User getUser(UUID userId) {
        for (User user : userList)
            if (user.getUserId().equals(userId))
                return user;

        return null;
    }
    public boolean addUser(User user){
        if (!userList.contains(user.getUserName())) {
            this.userList.add(user);
            return true;
        }
        else
            return false;

    }

    public boolean login(String userName, int password) {

        for (User user : userList) {
            if (user.getUserName().equals(userName) && user.getPassword()==(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkUserExist(String userName) {

        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    ////////// Insert , Edit , Delete task's method

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Task getTask(UUID idTask)
    {
        for(Task task:taskList)
            if (task.getTaskID().equals(idTask))
                return task;
            return null;
    }

    public List<Task> getTaskList(UUID userId)
    {
        for (User user:userList)
            if(user.getUserId().equals(userId))
                return user.getTaskList();
            return null;
    }
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
    private Repository() {
            userList = new ArrayList<>();
    }

}
