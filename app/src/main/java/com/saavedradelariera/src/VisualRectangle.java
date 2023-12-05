package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

//clase que permite dibujar un rectángulo gráficamente
public class VisualRectangle extends GameObject {


    //almacenamos un color y si es relleno o no
    ColorJ color;
    boolean fill;

    int radius = 0;

    //en el inicializador pasamos todos los atributos necesarios
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
    }

    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill, int radius) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
        this.radius = radius;
    }

    //overrideamos los métodos de GameObject y actualizamos render para que pinte lo deseado
    @Override
    public void Render(AndroidGraphics graphics) {
        if (radius > 0) {
            graphics.RenderFillRect(posX,posY,width,height,color, color, radius);
            return;
        }

        if(!fill) {
            graphics.RenderRect(posX, posY, width, height, color);
            return;
        }

        graphics.RenderFillRect(posX,posY,width,height,color, color);
    }

    @Override
    public void Update(AndroidEngine engine, float deltaTime) {

    }
}
