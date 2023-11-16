package com.saavedradelariera.src;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.FontJ;
import com.practica1.graphics.IGraphics;


//Clase text para dibujar textos
public class Text extends GameObject {

    //color y cadena de texto
    ColorJ c;
    String txt;
    FontJ font;
    boolean useFont = false;


    //Inicialización de un Text con fuente específica
    public Text(String route, int x, int y, int w, int h, String text, ColorJ c){
        super(x,y,w,h);

        this.c = c;
        this.txt = text;
        useFont = true;
        font = new FontJ(route, w, false);

    }

    //Inicialización de un Text sin fuente específica
    public Text( int x, int y, int w, int h, String text, ColorJ c){
        super(x,y,w,h);
        this.c = c;
        this.txt = text;
    }
     //Renderiza el texto con la fuente default o una especifica
    @Override
    public void Render(IGraphics graphics) {
        //Si usa la fuente la crea o setea
        if(useFont)
            graphics.CreateFont(font.getName(), font.getSize(), font.getBold());

        graphics.RenderText(posX, posY, width, txt, c);
    }

    @Override
    public void Update(IEngine iEngine, float deltaTime) {

    }

    public void SetText(String s){
        txt = s;
    }
}
