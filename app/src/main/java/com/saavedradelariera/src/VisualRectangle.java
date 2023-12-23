package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

/*clase que permite dibujar un rectángulo gráficamente con diferentes opciones*/
public class VisualRectangle extends GameObject {
    //almacenamos un color y si es relleno o no
    ColorJ color, color2;
    boolean fill;
    int radius = 0;

    //Crear un rectangulo de un solo color
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
    }

    //Crear un rectangulo con dos colores
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, ColorJ c2, boolean fill) {
        super(x, y, w, h);

        color = c;
        color2 = c2;
        this.fill = fill;
    }

    //Crear un rectangulo de un solo color redondeado
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill, int radius) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
        this.radius = radius;
    }

    //Crear un rectangulo con dos colores redondeado
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, ColorJ c2, boolean fill, int radius) {
        super(x, y, w, h);

        color = c;
        color2 = c2;
        this.fill = fill;
        this.radius = radius;
    }

    //overrideamos los métodos de GameObject y actualizamos render para que pinte lo deseado
    @Override
    public void render(AndroidGraphics graphics) {
        //segun las caracteristicas dadas en el constructor lo pintamos de diferentes maneras

        if (!fill) {
            graphics.renderRect(posX, posY, width, height, color);
            return;
        }

        if (radius > 0 && fill) {
            graphics.renderFillRect(posX, posY, width, height, color, color, radius);
            return;
        }

        if (color2 != null && fill) {
            graphics.renderFillRect(posX, posY, width, height, color, color2);
            return;
        }

        if (fill) {
            graphics.renderFillRect(posX, posY, width, height, color, color);
            return;
        }

    }

    @Override
    public void update(AndroidEngine engine, float deltaTime) {

    }
}
