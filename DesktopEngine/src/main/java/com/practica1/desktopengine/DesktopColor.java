package com.practica1.desktopengine;


import com.practica1.graphics.ColorJ;

import java.awt.Color;

/**
 * Clase encargada de crear los colores para Desktop
 */
public class DesktopColor extends ColorJ {

    private Color actualColor;

    public DesktopColor(int r, int g, int b) {
        super(r, g, b);
    }

    public void set(int R, int G, int B) {
        actualColor = new Color(R, G, B);
    }

    Color getActualColor() {
        return actualColor;
    }
}
