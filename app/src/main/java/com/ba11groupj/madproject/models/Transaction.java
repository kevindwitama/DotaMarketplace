package com.ba11groupj.madproject.models;

import java.util.Date;

public class Transaction {
    int transactionId;
    int userId;
    int itemId;
    int itemQty;
    Date transactionDate;

    public Transaction(int transactionId, int userId, int itemId, int itemQty, Date transactionDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.itemQty= itemQty;
        this.transactionDate = transactionDate;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getItemQty() {
        return itemQty;
    }
}
