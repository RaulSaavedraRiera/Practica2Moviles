package com.saavedradelariera.src;

public class Skin {
    private boolean bought;
    private int price;
    private String samplePath, skinsPath, title;

    public Skin(String title, int price, String samplePath, String skinsPath) {
        this.title = title;
        this.price = price;
        this.samplePath = samplePath;
        this.skinsPath = skinsPath;
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
}
