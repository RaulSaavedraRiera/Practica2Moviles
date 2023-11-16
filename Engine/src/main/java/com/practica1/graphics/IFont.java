package com.practica1.graphics;

/**
 * Interfaz para el uso de fuentes
 */
public interface IFont {

    String getName();
    boolean getBold();
    int getSize();

    void setSize(int size);
    void setBold(boolean to);

}
