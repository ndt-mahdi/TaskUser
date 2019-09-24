package com.example.taskuser.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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


   /* public boolean checkUserExist(String userName) {

        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }*/

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


    public List<Task> getTaskList(UUID userId)
    {
        for (User user:userList)
            if(user.getUserId()==(userId))
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
        this.taskList=new ArrayList<>();
        for (int i = 0; i <100 ; i++) {
            Task newtask=new Task();
            newtask.setTitle("Project "+(i+1));
            newtask.setDescription("First Exercies");
            newtask.setState(randomState());
            taskList.add(newtask);
        }
        userList = new ArrayList<>();
        User user=new User();
        user.setUserName("Admin");
        user.setTaskList(taskList);
        userList.add(user);
    }

    //this method is for test
    private TaskState randomState()
    {
        Random random=new Random();
        int randomState;
        TaskState taskState = TaskState.Todo;
        randomState=random.nextInt(3)+1;
        //Start Switch Task State
        switch (randomState)
        {
            case 1:{
                taskState= (TaskState.Todo);
                break;
            }
            case 2:{
                taskState= (TaskState.Doing);
                break;
            }
            case 3:{
                taskState= (TaskState.Done);
                break;
            }
        }
        //end Switch Task State
        return taskState;
    }
}