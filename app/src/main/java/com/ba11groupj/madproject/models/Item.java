package com.ba11groupj.madproject.models;

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
