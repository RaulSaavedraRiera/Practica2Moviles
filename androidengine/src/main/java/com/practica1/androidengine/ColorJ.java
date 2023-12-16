package com.practica1.androidengine;

/**
 * Clase que se encarga de guardar los datos sobre los colores
 */
public class ColorJ {

    int  r = 255;
    int  g = 255;
    int  b = 255;
    int a = 255;

    public ColorJ(int R, int G, int B)
    {
        this.setRGB(R, G, B);
    }

    public ColorJ(int A, int R, int G, int B)
    {
        this.setRGB(R, G, B);
        this.a = A;
    }

    public ColorJ(String color) {
        int red = Integer.parseInt(color.substring(1, 3), 16);
        int green = Integer.parseInt(color.substring(3, 5), 16);
        int blue = Integer.parseInt(color.substring(5, 7), 16);

        this.setRGB(red,green,blue);
    }

    public void setRGB(int R, int G, int B){
        r = R;
        b = B;
        g = G;
    };

    public void setA(int A){
        this.a = A;
    }

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getA() {
        return a;
    }
}
