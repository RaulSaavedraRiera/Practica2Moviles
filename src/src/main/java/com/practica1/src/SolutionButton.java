package com.practica1.src;

import com.practica1.engine.IEngine;
import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;

import java.awt.Color;

/*clase que contiene los botones de pista que mostramos en las filas*/
public class SolutionButton extends Button{

    //Creamos un SolutionButton, seteando todos los valores iniciales y damos su color por defecto
    SolutionButton(int x, int y, int w, int h, float smallCircle ) {
        super(x, y, w, h, smallCircle);


        color.setRGB(180, 180, 180);
        backgroundColor.setRGB(180, 180, 180);
    }

    //overrideamos los métodos de GameObject y actualizamos render para que pinte lo deseado
    @Override
    public void Render(IGraphics graphics) {
        super.Render(graphics);
    }

    @Override
    public void Update(IEngine IEngine, float deltaTime) {

    }


    //una vez dada la información de pista a representar; ajusta sus valores para mostrarlo gráficamente
    public void SetSolution(int i){
        switch(i){
            case 0:
                SetSmallCircleSize(1f);
                color.setRGB(0,0,0);
                backgroundColor.setRGB(255,255,255);
                break;
            case 1:
                SetSmallCircleSize(0.85f);
                backgroundColor.setRGB(0,0,0);
                color.setRGB(255,255,255);
                break;
            case 2:
                break;
        }


    }


}
