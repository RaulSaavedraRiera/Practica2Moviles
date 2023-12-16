package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

//clase que permite dibujar un rectángulo gráficamente
public class VisualRectangle extends GameObject {


    //almacenamos un color y si es relleno o no
    ColorJ color, color2;
    boolean fill;

    int radius = 0;

    //en el inicializador pasamos todos los atributos necesarios
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
    }

    public VisualRectangle(int x, int y, int w, int h, ColorJ c, ColorJ c2, boolean fill) {
        super(x, y, w, h);

        color = c;
        color2 = c2;
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
        if(!fill) {
            graphics.RenderRect(posX, posY, width, height, color);
            return;
        }

        if (radius > 0 && fill) {
            graphics.RenderFillRect(posX,posY,width,height,color, color, radius);
            return;
        }

        if (color2 != null && fill) {
            graphics.RenderFillRect(posX,posY,width,height,color, color2);
            return;
        }

        if (fill) {
            graphics.RenderFillRect(posX,posY,width,height,color, color);
            return;
        }
    }

    @Override
    public void Update(AndroidEngine engine, float deltaTime) {

    }
}
