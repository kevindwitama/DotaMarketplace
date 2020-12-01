package com.ba11groupj.madproject.models;

public class User {

    public User(String name, String username, String password, String phoneNum, boolean genderIsM, int balance) {
        this.userId = "U" + username;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.genderIsM = genderIsM;
        this.balance = balance;
    }

    private String userId;
    private String name;
    private String username;
    private String password;
    private String phoneNum;
    private boolean genderIsM;
    private int balance;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
