package com.practica1.src.Buttons;

import com.practica1.input.TouchEvent;
import com.practica1.src.GameObject;
import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;

/*Clase base para los botones de cambio de escena*/
public class ChangeSceneButton extends GameObject {

    private ColorJ c = new ColorJ(173, 216, 230);
    private ColorJ c2 = new ColorJ(173, 216, 230);
    private IGraphics g;

    private int X;
    private int Y;
    private int W;
    private int H;


    public ChangeSceneButton(int x, int y, int w, int h, ColorJ c, ColorJ c2) {
        super(x, y, w, h);
        this.c = c;
        this.c2 = c2;
        this.g = g;
        X = x;
        Y = y;
        W = w;
        H = h;
    }

    //Los botones serán representados como rectángulos
    @Override
    public void Render(IGraphics graphics) {
        graphics.RenderFillRect(posX, posY, width, height, c, c2);
    }

    @Override
    public void Update(IEngine iEngine, float deltaTime) {
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
