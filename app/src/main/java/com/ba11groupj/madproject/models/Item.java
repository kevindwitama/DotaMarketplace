package com.ba11groupj.madproject.models;

public class Item {
    private String id;
    private String name;
    private int price;
    private int stock;
    private int latitude;
    private int longitude;

    public Item(String id, String name, int price, int stock, int latitude, int longitude) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
