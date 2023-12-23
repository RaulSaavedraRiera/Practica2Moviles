package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

//Clase text para dibujar textos
public class ColorBackground extends GameObject {
    //color y cadena de texto
    ColorJ c;

    //Inicialización de un Text con fuente específica
    public ColorBackground(ColorJ c) {
        super(0, 0, 0, 0);
        this.c = c;
    }

    //Renderiza el texto con la fuente default o una especifica
    @Override
    public void render(AndroidGraphics graphics) {
        graphics.CleanScreen(c);
    }
}
