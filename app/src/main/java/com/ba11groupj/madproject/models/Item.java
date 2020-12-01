package com.ba11groupj.madproject.models;

public class Item {
    private int id;
    private String name;
    private int price;
    private int stock;
    private float latitude;
    private float longitude;

    public Item(int id, String name, int price, int stock, float latitude, float longitude) {
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

    public void setId(int id) {
        this.id = id;
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

    public void setStock(int stock) {
        this.stock = stock;
    }

}
