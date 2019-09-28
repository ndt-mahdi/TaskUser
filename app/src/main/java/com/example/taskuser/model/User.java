package com.example.taskuser.model;

import java.util.UUID;

public class User {
    private UUID userId;
    private String userName;
    private int password;

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

    public User() {
        this.userId = UUID.randomUUID();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getPassword() == user.getPassword() &&
                getUserName().equals(user.getUserName());
    }



}
