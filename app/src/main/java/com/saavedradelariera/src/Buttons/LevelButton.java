package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.GameObject;
import com.saavedradelariera.src.Text;

/*Clase base para los botones de cambio de escena*/
public class LevelButton extends GameObject {
    private ColorJ c;
    private ColorJ c2;
    private int X;
    private int Y;
    private int W;
    private int H;
    private int id;

    public LevelButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, int id, String font) {
        super(x, y, w, h);
        this.c = c;
        this.c2 = c2;
        X = x;
        Y = y;
        W = w;
        H = h;
        this.id = id;

        Text t = new Text(font,posX + width/3, posY +height/3, W/3, height/3, String.valueOf(id), c2);

    }

    //Los botones serán representados como rectángulos
    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderFillRect(posX, posY, width, height, c, c2);
    }



    //Comprobamos si se ha pulsado sobre el botón y si es así llamamos al método HandleClick para procesarlo
    @Override
    public boolean HandleInput(TouchEvent e)
    {

        if(e.getType() == TouchEvent.TouchEventType.CLICK || e.getType() == TouchEvent.TouchEventType.TOUCH_UP)
        {
            if (ISOver(e.getX(), e.getY())) {

              return HandleClick();
            }
        }

        return false;
    }


    public int getH() {
        return H;
    }

    public int getW() {
        return W;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    //Método para procesar dicho click
    protected boolean HandleClick(){
        return false;
    }
}
