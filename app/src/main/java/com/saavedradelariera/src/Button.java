package com.saavedradelariera.src;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;

/*Clase base botón para crear tanto lso botones de color como los de las pistas*/
public abstract class Button extends GameObject {

    private float smallCirclePercent;
    private int posXSmallCircle, posYSmallCircle;

    protected ColorJ color;
    protected ColorJ  backgroundColor;
    protected boolean enable = false;

    //Genera un botón con el tamaño, colores y porcentaje del circulo pequeño dados
    Button(int x, int y, int w, int h, float smallCircle){

        super(x,y,w,h);
        smallCirclePercent = smallCircle;

        this.color = new ColorJ(80, 80, 80);
        this.backgroundColor = new ColorJ(180, 180, 180);

        SetSmallCircleSize(smallCircle);
    }

    public void Render(IGraphics graphics) {

        graphics.RenderCircle(posX,posY,width/2,this.backgroundColor);
        graphics.RenderCircle( posX + posXSmallCircle, posY + posYSmallCircle,(int)(width*smallCirclePercent)/2,this.color);
    }

    //ajusta el diámetro del circulo interior
    public void SetSmallCircleSize(float size){

        smallCirclePercent = size;

        posXSmallCircle = posYSmallCircle = (int)((width - (width*smallCirclePercent))/2);
    }

    public void SetColor(ColorJ c)
    {
        color = c;
    }

    public void SetBackgroundColor(ColorJ c)
    {
        backgroundColor = c;
    }

    public void SetColors(ColorJ c, ColorJ backgroundC)
    {
        color = c;
        backgroundColor = backgroundC;
    }
}
