package com.example.taskuser.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    private static Repository ourInstance;
    private List<User> userList;
   // private List<Task> taskList;
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

    private Repository() {
            userList = new ArrayList<>();
    }

}
