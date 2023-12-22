package com.saavedradelariera.src;

/* Esta clase se usa para representar las skins de los colores. Hereda todas las funcionalidades de
Skin y adicionalmente almacena los dos colores que tiene.*/

public class ColorSkin extends Skin {
    private String primaryColor, secondaryColor;

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public ColorSkin(String title, int price, String samplePath, String skinsPath, String category, String primaryColor, String secondaryColor) {
        super(title, price, samplePath, skinsPath, category);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
