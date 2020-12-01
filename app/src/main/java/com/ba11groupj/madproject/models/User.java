package com.ba11groupj.madproject.models;

public class User {

    public User(int userId, String fullName, String username, String password, String phoneNum, boolean genderIsM, float balance) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.genderIsM = genderIsM;
        this.balance = balance;
    }

    private int userId;
    private String fullName;
    private String username;
    private String password;
    private String phoneNum;
    private boolean genderIsM;
    private float balance;

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
