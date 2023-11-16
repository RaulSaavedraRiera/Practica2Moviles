package com.practica1.androidengine;

/**
 * Clase que se encarga de guardar los datos sobre los colores
 */
public class ColorJ {

    int  r = 255;
    int  g = 255;
    int  b = 255;

    public ColorJ(int R, int G, int B)
    {
        this.setRGB(R, G, B);
    }

    public void setRGB(int R, int G, int B){
        r = R;
        b = B;
        g = G;
    };

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }
}
