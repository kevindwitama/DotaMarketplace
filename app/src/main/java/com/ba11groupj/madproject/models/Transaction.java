package com.ba11groupj.madproject.models;

public class Transaction {
    final int transactionId;
    final int userId;
    final int itemId;
    final int itemQty;
    final String transactionDate;

    public Transaction(int transactionId, int userId, int itemId, int itemQty, String transactionDate) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.itemQty = itemQty;
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

    public String getTransactionDate() {
        return transactionDate;
    }

    public int getItemQty() {
        return itemQty;
    }
}
