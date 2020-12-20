package com.github.kevindwitama.dotamarketplace.models;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 *
 * Dota Marketplace
 *
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

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

    private final int userId;
    private final String fullName;
    private final String username;
    private final String password;
    private final String phoneNum;
    private final boolean genderIsM;
    private final float balance;

    public int getId() {
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

}
