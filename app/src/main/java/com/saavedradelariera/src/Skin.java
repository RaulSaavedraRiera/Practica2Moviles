package com.saavedradelariera.src;

public class Skin {
    public Skin(String title, int price, String path) {
        this.title = title;
        //this.bought = bought;
        this.price = price;
        this.path = path;
    }

    private String title;
    private boolean bought;
    private int price;
    private String path;

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBought() {
        return bought;
    }

    public int getPrice() {
        return price;
    }
}
