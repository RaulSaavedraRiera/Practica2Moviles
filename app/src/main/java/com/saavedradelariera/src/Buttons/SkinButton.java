package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.GameObject;
import com.saavedradelariera.src.Text;

public class SkinButton extends GameObject {
    private final String coinImgPath = "coin.png";
    private ColorJ c;
    private int X;
    private int Y;
    private int W;
    private int H;
    private int id;
    private String imagePath;

    public SkinButton(int x, int y, int w, int h, ColorJ c, int id, String font, String imagePath, int price) {
        super(x, y, w, h);
        this.c = c;
        X = x;
        Y = y;
        W = w;
        H = h;
        this.id = id;
        this.imagePath = imagePath;

        Text priceText = new Text(font, x + 55, y + 190, 60, 60, "" + price, c);
    }

    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderImage(graphics.createImage(imagePath), X, Y, W, H);
    }

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
    protected boolean HandleClick() {
        return false;
    }
}
