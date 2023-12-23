package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;

/*Clase base botón para crear tanto los botones de color como los de las pistas*/
public abstract class Button extends GameObject {

    private float smallCirclePercent;
    private int posXSmallCircle, posYSmallCircle;

    protected ColorJ color, backgroundColor;
    protected boolean enable = false;

    //Genera un botón con el tamaño, colores y porcentaje del circulo pequeño dados
    Button(int x, int y, int w, int h, float smallCircle) {

        super(x, y, w, h);
        smallCirclePercent = smallCircle;

        this.color = new ColorJ(80, 80, 80);
        this.backgroundColor = new ColorJ(180, 180, 180);

        setSmallCircleSize(smallCircle);
    }

    // Renderiza el botón
    public void render(AndroidGraphics graphics) {
        graphics.renderCircle(posX, posY, width / 2, this.backgroundColor);
        graphics.renderCircle(posX + posXSmallCircle, posY + posYSmallCircle, (int) (width * smallCirclePercent) / 2, this.color);
    }

    //ajusta el diámetro del circulo interior
    public void setSmallCircleSize(float size) {
        smallCirclePercent = size;
        posXSmallCircle = posYSmallCircle = (int) ((width - (width * smallCirclePercent)) / 2);
    }

    public void setColor(ColorJ c) {
        color = c;
    }

    public void setBackgroundColor(ColorJ c) {
        backgroundColor = c;
    }

    public boolean isEnabled() {
        return enable;
    }
}
