package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/*Clase que recoge el input del jugador para ir creando su intento de solución*/
public class InputSolution extends GameObject {

    ButtonArray buttons;
    ArrayList<Integer> writeArray;

    VisualRectangle vRectangle;

    final float spaceCoefficient = 0.9f, offsetBtwButtons = 1.1f;

    //Genera los botones sobre los que registrar la solucion así como la parte gráfica
    public InputSolution(int x, int y, int w, int h, int tam, ColorJ rectangleColor, ArrayList<ColorJ> c, boolean daltonic)
    {
        super(x,y,w,h);
        writeArray = new ArrayList<Integer>(tam);
        buttons = new ButtonArray(x,y,w,h);

        //añade los numeros y almacena los colores para usarlos más adelante
        ArrayList<Integer> nums = new ArrayList<>();
        ArrayList<ColorJ> colors = c;

        for (int i = 0; i < tam; i++) {
            nums.add(i);
        }
        //genera los botones y los activa
        buttons.generateEnableButtons(tam, spaceCoefficient, offsetBtwButtons, 1f, nums, colors, true, false, daltonic);
        vRectangle = new VisualRectangle(x,y,w,h, rectangleColor, true);
    }

    public InputSolution(int x, int y, int w, int h, int tam, ColorJ rectangleColor, ArrayList<AndroidImage> imgs)
    {
        super(x,y,w,h);
        writeArray = new ArrayList<Integer>(tam);


        buttons = new ButtonArray(x,y,w,h);

        //añade los numeros y almacena los colores para usarlos más adelante
        ArrayList<Integer> nums = new ArrayList<>();

        for (int i = 0; i < tam; i++) {
            nums.add(i);
        }
        //genera los botones y los activa
        buttons.generateEnableButtons(tam, spaceCoefficient, offsetBtwButtons, 1f, nums, imgs, true, false);
        vRectangle = new VisualRectangle(x,y,w,h, rectangleColor, true);
    }


    @Override
    public boolean handleInput(TouchEvent e)
    {
       return buttons.handleInput(e);
    }

}
