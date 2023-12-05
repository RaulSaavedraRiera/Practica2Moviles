package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.GameObject;

/*Clase base para los botones de cambio de escena*/
public class GenericButton extends GameObject {
    private ClickListener clickListener;
    private ColorJ c, c2;
    private int x, y, w, h;
    private int radius;

    public GenericButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, int radius) {
        super(x, y, w, h);
        this.c = c;
        this.c2 = c2;
        this.x = x;
        this.y = y;
        this.h = h;
        this.radius = radius;
    }


    //Los botones serán representados como rectángulos
    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderFillRect(posX, posY, width, height, c, c2, radius);
    }

    //Comprobamos si se ha pulsado sobre el botón y si es así llamamos al método HandleClick para procesarlo
    @Override
    public boolean HandleInput(TouchEvent e) {
        if (e.getType() == TouchEvent.TouchEventType.CLICK || e.getType() == TouchEvent.TouchEventType.TOUCH_UP) {
            if (ISOver(e.getX(), e.getY())) {

                return HandleClick();
            }
        }
        return false;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Método para procesar dicho click
    protected boolean HandleClick() {
        if (clickListener != null) {
            clickListener.onClick();
        }
        return true;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }
}
