package com.practica1.src;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;


//clase que permite dibujar un rectángulo gráficamente
public class VisualRectangle extends GameObject {


    //almacenamos un color y si es relleno o no
    ColorJ color;
    boolean fill;

    //en el inicializador pasamos todos los atributos necesarios
    public VisualRectangle(int x, int y, int w, int h, ColorJ c, boolean fill) {
        super(x, y, w, h);

        color = c;
        this.fill = fill;
    }


    //overrideamos los métodos de GameObject y actualizamos render para que pinte lo deseado
    @Override
    public void Render(IGraphics graphics) {
        if(!fill) {
            graphics.RenderRect(posX, posY, width, height, color);
        }
        else
            graphics.RenderFillRect(posX,posY,width,height,color, color);
    }

    @Override
    public void Update(IEngine engine, float deltaTime) {

    }
}
