package com.saavedradelariera.src;

/*Clase que guarda el estado del mundo actual, tanto sus skins como su fondo*/
public class WorldStyle {
    private String background;
    private int idSkins;

    WorldStyle(String background, int id) {
        this.background = background;
        this.idSkins = id;
    }

    public String getBackground() {
        return background;
    }

    public int getIdSkins() {
        return idSkins;
    }
}