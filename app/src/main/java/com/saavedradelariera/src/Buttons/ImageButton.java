package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.GameObject;


/*Clase base para los botones con imagen*/
public class ImageButton extends GameObject {

    AndroidImage image;
    private int X;
    private int Y;
    private int W;
    private int H;

    public ImageButton(String route,int x, int y, int w, int h ){
        super(x,y,w,h);

        image = new AndroidImage();
        image.setRoute(route);
        X = x;
        Y = y;
        W = w;
        H = h;
    }

    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderImage(graphics.createImage(image.getRoute()),X, Y, W, H);
    }


    //Comprobamos si se ha pulsado sobre el botón y si es así llamamos al método HandleClick para procesarlo
    @Override
    public boolean HandleInput(TouchEvent e)
    {
        if(e.getType() == TouchEvent.TouchEventType.TOUCH_DOWN)
        {
           if(ISOver(e.getX(), e.getY()))
           {
               return HandleClick();
           }
        }
        return false;
    }

    public int getX() {
        return X;
    }

    public int getW() {
        return W;
    }

    public int getH() {
        return H;
    }

    public int getY() {
        return Y;
    }

    //Método para procesar dicho click
    protected boolean HandleClick(){
        return false;
    }
}