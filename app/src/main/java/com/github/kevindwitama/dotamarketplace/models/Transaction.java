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
