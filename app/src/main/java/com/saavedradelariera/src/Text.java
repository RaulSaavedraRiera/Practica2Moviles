package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidFont;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

/*Clase text para dibujar textos en pantalla con posibilidad de modificar color y fuente*/
public class Text extends GameObject {

    //color y cadena de texto
    ColorJ c;
    String txt;
    AndroidFont font;
    boolean useFont = false;


    //Inicialización de un Text con fuente específica
    public Text(String route, int x, int y, int w, int h, String text, ColorJ c) {
        super(x, y, w, h);

        this.c = c;
        this.txt = text;
        useFont = true;
        font = new AndroidFont(route, w, false);
    }

    //Inicialización de un Text sin fuente específica
    public Text(int x, int y, int w, int h, String text, ColorJ c) {
        super(x, y, w, h);
        this.c = c;
        this.txt = text;
    }

    //Renderiza el texto con la fuente default o una especifica
    @Override
    public void Render(AndroidGraphics graphics) {
        //Si usa la fuente la crea o setea
        if (useFont)
            graphics.CreateFont(font.getRoute(), font.getSize(), font.getBold());

        graphics.RenderText(posX, posY, width, txt, c);
    }

    @Override
    public void Update(AndroidEngine iEngine, float deltaTime) {

    }

    public void setText(String s) {
        txt = s;
    }
}
