package com.saavedradelariera;

import com.saavedradelariera.src.Skin;

public class ColorSkin extends Skin {
    private String primaryColor;

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    private String secondaryColor;

    public ColorSkin(String title, int price, String samplePath, String skinsPath, String category, String primaryColor, String secondaryColor) {
        super(title, price, samplePath, skinsPath, category);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
