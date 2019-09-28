package com.example.taskuser.model;


import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Repository {
    private static Repository ourInstance;
    private List<User> userList = new ArrayList<>();
    private List<Task> taskList = new ArrayList<>();


    public static Repository getInstance() {
        if (ourInstance == null)
            ourInstance = new Repository();
        return ourInstance;
    }

    public User getUser(UUID userId) {
        for (User user : userList)
            if (user.getUserId() == (userId))
                return user;

        return null;
    }

    public boolean addUser(User user) {
        if (!userList.contains(user.getUserName())) {
            this.userList.add(user);
            return true;
        } else
            return false;

    }

    public boolean login(String userName, int password) {

        for (User user : userList) {
            if (user.getUserName().equals(userName) && user.getPassword() == (password)) {
                return true;
            }
        }
        return false;
    }


    ////////// Insert , Edit , Delete task's method

    public UUID checkUserExist(String userName) {

        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                return user.getUserId();
            }
        }
        return null;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }


    public List<Task> getTaskList(UUID userId) {
        List<Task> newTask = new ArrayList<>();
        for (Task task : taskList) {
            if (getUser(userId).getUserId().equals(task.getUserIdForeign())) {
                newTask.add(task);
            }
        }
        return newTask;
    }

    public void insertTask(Task task) {
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


        User user = new User();
        user.setUserName("Admin");
        user.setPassword(12345);
        addUser(user);

        user = new User();
        user.setUserName("mahdi");
        user.setPassword(42245);
        addUser(user);


        for (int i = 0; i < 100; i++) {
            Task newtask = new Task();
            newtask.setTitle("Project " + (i + 1));
            newtask.setDescription("This App Is For Test");
            newtask.setState(randomState());
            newtask.setUserIdForeign(randomUser());
            insertTask(newtask);
        }

    }

    private UUID randomUser() {
        Random random = new Random();
        int randomUserIndex;
        randomUserIndex = random.nextInt(userList.size());
        return userList.get(randomUserIndex).getUserId();
    }

    //this method is for test
    private TaskState randomState() {
        Random random = new Random();
        int randomState;
        TaskState taskState = TaskState.Todo;
        randomState = random.nextInt(3) + 1;
        //Start Switch Task State
        switch (randomState) {
            case 1: {
                taskState = (TaskState.Todo);
                break;
            }
            case 2: {
                taskState = (TaskState.Doing);
                break;
            }
           /*case 3:{
                taskState= (TaskState.Done);
                break;
            }*/
        }
        //end Switch Task State
        return taskState;
    }

}
