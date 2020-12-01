package com.ba11groupj.madproject.models;

import java.util.Date;

public class Transaction {
    String transactionId;
    String userId;
    String itemId;

    int itemQty;
    Date transactionDate;

    public Transaction(String userId, String itemId, int itemQty, Date transactionDate) {
        this.transactionId = "T" + transactionDate + userId;
        this.userId = userId;
        this.itemId = itemId;
        this.itemQty= itemQty;
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getItemQty() {
        return itemQty;
    }
}
