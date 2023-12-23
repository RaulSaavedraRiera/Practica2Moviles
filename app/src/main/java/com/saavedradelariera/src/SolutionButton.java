package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;

/*clase que contiene los botones de pista que mostramos en las filas*/
public class SolutionButton extends Button {

    //Creamos un SolutionButton, seteando todos los valores iniciales y damos su color por defecto
    SolutionButton(int x, int y, int w, int h, float smallCircle) {
        super(x, y, w, h, smallCircle);


        color.setRGB(180, 180, 180);
        backgroundColor.setRGB(180, 180, 180);
    }

    //overrideamos los métodos de GameObject y actualizamos render para que pinte lo deseado
    @Override
    public void render(AndroidGraphics graphics) {
        super.render(graphics);
    }

    @Override
    public void update(AndroidEngine IEngine, float deltaTime) {

    }


    //una vez dada la información de pista a representar; ajusta sus valores para mostrarlo gráficamente
    public void setSolution(int i) {
        switch (i) {
            case 0:
                setSmallCircleSize(1f);
                color.setRGB(0, 0, 0);
                backgroundColor.setRGB(255, 255, 255);
                break;
            case 1:
                setSmallCircleSize(0.85f);
                backgroundColor.setRGB(0, 0, 0);
                color.setRGB(255, 255, 255);
                break;
            case 2:
                break;
        }


    }


}
