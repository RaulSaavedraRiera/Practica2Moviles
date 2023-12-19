package com.saavedradelariera.src;

public class Skin {
    private boolean bought;
    private int price;
    private String samplePath, skinsPath, title, category;

    public String getCategory() {
        return category;
    }

    public Skin(String title, int price, String samplePath, String skinsPath, String category) {
        this.title = title;
        this.price = price;
        this.samplePath = samplePath;
        this.skinsPath = skinsPath;
        this.category = category;
    }

    public Skin(String title, int price, String samplePath, String skinsPath, String category, boolean bought) {
        this.title = title;
        this.price = price;
        this.samplePath = samplePath;
        this.skinsPath = skinsPath;
        this.category = category;
        this.bought = bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String getSamplePath() {
        return samplePath;
    }

    public String getTitle() {
        return title;
    }

    public boolean getBought() {
        return bought;
    }

    public int getPrice() {
        return price;
    }

    public String getSkinsPath() {
        return skinsPath;
    }
}
