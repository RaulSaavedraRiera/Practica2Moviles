package com.saavedradelariera.src;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;

import java.util.ArrayList;


/*Clase que almacena una fila de botones y permtie su gestión centralizada*/
public class ButtonArray extends GameObject {

    ArrayList<CombinationButton> buttons = new ArrayList<>();

    int buttonSelected;

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
    public void GenerateEnableButtons(int n, float spaceCoefficient, float offsetBtwButtons,
                                      float smallCircle, ArrayList<Integer> num, ArrayList<ColorJ> colors, boolean inputEnable
                                      ,boolean cleanColorEnable, boolean daltonic) {


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
            b =  new CombinationButton(
                    posX + initialOffset + zoneButton * i, posY + offsetY, widthButton, widthButton, smallCircle, i);
            b.EnableCombinationButton(colors.get(num.get(i)), num.get(i), 1f, inputEnable, cleanColorEnable, daltonic);
            buttons.add(b);
        }
    }


    //desactiva el input de todos los botones
    public void DisableInputButtons(){
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).DisableInput();
        }
    }

    @Override
    public void Render(IGraphics iGraphics) {
    }

    @Override
    public void Update(IEngine IEngine, float deltaTime) {

    }


  //activa el boton seleccionado con los valores dados; permitiendo diferentes comportamientos y color
    public void Enablebutton(int i, ColorJ c, int n,float smallCircle, boolean inputEnabled, boolean cleanColorEnable, boolean daltonicCurrentEnabled){
     buttons.get(i).EnableCombinationButton(c,n,smallCircle, inputEnabled, cleanColorEnable, daltonicCurrentEnabled);
    }

    //devuelve el n de botones
    public int GetNButtons(){
        return buttons.size();
    }

    //Pasa el input a los botones
    public boolean HandleInput(TouchEvent e) {

        for (int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).HandleInput(e))
                return true;
        }
        return super.HandleInput(e);
    }


    //busca y devuelve el primer boton no activo
    public int GetFirstAvailableButton(){
        for (int i = 0; i < buttons.size(); i++) {
            if(!buttons.get(i).enable)
                return i;
        }

        return buttons.size();
    }
}
