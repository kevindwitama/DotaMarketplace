package com.github.kevindwitama.dotamarketplace.models;

/**
 * Final Project ISYS6203 Mobile Application Development
 * Lab BL11 / XB11
 * <p>
 * Dota Marketplace
 * <p>
 * Contributed by
 * 2201825535 - Kevin Dwitama Putra
 * 2201836330 - Natasha Anugrah
 */

public class Item {
    private final int id;
    private final String name;
    private final int price;
    private final int stock;
    private final double latitude;
    private final double longitude;

    public Item(int id, String name, int price, int stock, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
