package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.GameObject;


/*Clase base para los botones con imagen*/
public class ImageButton extends GameObject {

    AndroidImage image;

    ColorJ c = new ColorJ(255, 255, 255);
    private int X;
    private int Y;
    private int W;
    private int H;
    private boolean cleanScreen = false;

    private ClickListener clickListener;

    public ImageButton(String route, int x, int y, int w, int h) {
        super(x, y, w, h);
        image = new AndroidImage();
        image.setRoute(route);
        X = x;
        Y = y;
        W = w;
        H = h;
    }

    @Override
    public void render(AndroidGraphics graphics) {
        if (cleanScreen) {
            graphics.cleanScreen(c);
        }
        graphics.renderImage(graphics.createImage(image.getRoute()), X, Y, W, H);
    }


    //Comprobamos si se ha pulsado sobre el botón y si es así llamamos al método HandleClick para procesarlo
    @Override
    public boolean handleInput(TouchEvent e) {
        if (e.getType() == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (isOver(e.getX(), e.getY())) {
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
