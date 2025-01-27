package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;


/*Clase que almacena una fila de botones y permtie su gestión centralizada*/
public class ButtonArray extends GameObject {

    ArrayList<CombinationButton> buttons = new ArrayList<>();

    public ButtonArray(int x, int y, int w, int h) {
        super(x, y, w, h);
    }


    //Crea los botones en fila
    public void GenerateButtons(int n, float spaceCoefficient, float offsetBtwButtons, float smallCircle) {


        //calculos de posición y offset generales y entre botones
        int zoneButton = (int) ((width / n) * spaceCoefficient);
        int widthButton = (int) ((width / (n * offsetBtwButtons)) * spaceCoefficient);

        if (zoneButton > height * spaceCoefficient) {
            zoneButton = (int) (height * spaceCoefficient);
            widthButton = (int) (zoneButton / offsetBtwButtons);
        }


        int initialOffset = (int) ((width - zoneButton * n) / 2);

        int offsetY = (int) ((height - widthButton) / 2);

        //generación de lso botones
        for (int i = 0; i < n; i++) {
            buttons.add((
                    new CombinationButton(
                            posX + initialOffset + zoneButton * i, posY + offsetY, widthButton, widthButton, smallCircle, i)));
        }
    }

    //genera botones ya inicalizdos, usado en InputSolution
    public void generateEnableButtons(int n, float spaceCoefficient, float offsetBtwButtons,
                                      float smallCircle, ArrayList<Integer> num, ArrayList<ColorJ> colors, boolean inputEnable
            , boolean cleanColorEnable, boolean daltonic) {


        //calculos de posicion similares al anterior
        int zoneButton = (int) ((width / n) * spaceCoefficient);
        int widthButton = (int) ((width / (n * offsetBtwButtons)) * spaceCoefficient);

        if (zoneButton > height * spaceCoefficient) {
            zoneButton = (int) (height * spaceCoefficient);
            widthButton = (int) (zoneButton / offsetBtwButtons);
        }


        int initialOffset = (int) ((width - zoneButton * n) / 2);

        int offsetY = (int) ((height - widthButton) / 2);

        CombinationButton b;


        //los genera con los comportamientos y colores dados
        for (int i = 0; i < n; i++) {
            b = new CombinationButton(
                    posX + initialOffset + zoneButton * i, posY + offsetY, widthButton, widthButton, smallCircle, i);
            b.enableCombinationButton(colors.get(num.get(i)), num.get(i), 1f, inputEnable, cleanColorEnable, daltonic);
            buttons.add(b);
        }
    }

    //version para imagenes
    public void generateEnableButtons(int n, float spaceCoefficient, float offsetBtwButtons,
                                      float smallCircle, ArrayList<Integer> num, ArrayList<AndroidImage> imgs, boolean inputEnable, boolean deleteColorButton) {


        //calculos de posicion similares al anterior
        int zoneButton = (int) ((width / n) * spaceCoefficient);
        int widthButton = (int) ((width / (n * offsetBtwButtons)) * spaceCoefficient);

        if (zoneButton > height * spaceCoefficient) {
            zoneButton = (int) (height * spaceCoefficient);
            widthButton = (int) (zoneButton / offsetBtwButtons);
        }


        int initialOffset = (int) ((width - zoneButton * n) / 2);

        int offsetY = (int) ((height - widthButton) / 2);

        CombinationButton b;


        //los genera con los comportamientos y colores dados
        for (int i = 0; i < n; i++) {
            b = new CombinationButton(
                    posX + initialOffset + zoneButton * i, posY + offsetY, widthButton, widthButton, smallCircle, i);
            b.enableCombinationButton(imgs.get(num.get(i)), num.get(i), 1f, inputEnable, deleteColorButton);
            buttons.add(b);
        }
    }


    //desactiva el input de todos los botones
    public void DisableInputButtons() {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).disableInput();
        }
    }

    @Override
    public void render(AndroidGraphics iGraphics) {
    }

    @Override
    public void update(AndroidEngine IEngine, float deltaTime) {

    }


    //activa el boton seleccionado con los valores dados; permitiendo diferentes comportamientos y color
    public void enableButton(int i, ColorJ c, int n, float smallCircle, boolean inputEnabled, boolean cleanColorEnable, boolean daltonicCurrentEnabled) {
        buttons.get(i).enableCombinationButton(c, n, smallCircle, inputEnabled, cleanColorEnable, daltonicCurrentEnabled);
    }

    //version para imagenes
    public void enableButton(int i, AndroidImage img, int n, float smallCircle, boolean inputEnabled, boolean cleanColorEnable) {
        buttons.get(i).enableCombinationButton(img, n, smallCircle, inputEnabled, cleanColorEnable);
    }

    //devuelve el n de botones
    public int getNButtons() {
        return buttons.size();
    }

    public String getButtonsCombination() {
        String s = "";
        for (CombinationButton b : buttons)
            if (b.isEnabled())
                s += String.valueOf(b.getNumber());

        return s;
    }

    public ArrayList<CombinationButton> getButtons() {
        return buttons;
    }

    //Pasa el input a los botones
    public boolean handleInput(TouchEvent e) {

        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).handleInput(e))
                return true;
        }
        return super.handleInput(e);
    }

    //busca y devuelve el primer boton no activo
    public int getFirstAvailableButton() {
        for (int i = 0; i < buttons.size(); i++) {
            if (!buttons.get(i).enable)
                return i;
        }

        return buttons.size();
    }
}
