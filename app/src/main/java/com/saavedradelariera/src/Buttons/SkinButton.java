package com.saavedradelariera.src.Buttons;

import android.widget.Toast;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.GameObject;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Skin;

public class SkinButton extends GameObject {
    private final String coinImgPath = "coin.png";
    private ColorJ c;
    private int X,Y,W,H;
    private Skin skin;
    private ClickListener clickListener;

    public SkinButton(Skin skin, int x, int y, int w, int h, ColorJ c, String font, String imagePath, int price) {
        super(x, y, w, h);
        this.c = c;
        X = x;
        Y = y;
        W = w;
        H = h;
        this.skin = skin;
    }

    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderImage(graphics.createImage(skin.getSamplePath()), X, Y, W, H);
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
    protected boolean HandleClick(){


        clickListener.onClick();
        return true;
    }

    public void setClickListener(ClickListener listener) {
        this.clickListener = listener;
    }
}
