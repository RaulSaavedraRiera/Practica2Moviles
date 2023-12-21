package com.saavedradelariera.src;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;

import java.util.ArrayList;

/*
 * clase línea que contiene todos los elementos necesarios de esta:
 * - Botones para mostrar los colores
 * - Botones para mostrar la comparación de la combinación dada con la solución
 * - Calculo de posiciones de lo anterior
 * - Actualziar el valor de un botón al asignarle un color
 *

 * */
public class Row extends GameObject {

    //variables que necesitamos para almacenar la información de la línea
    ArrayList<SolutionButton> solutionButtons = new ArrayList<>();
    ArrayList<GameObject> objectsInRow = new ArrayList<>();

    ButtonArray combinationArray;

    //variables numericas para el formato de la columna
    final float offsetButtons = 1.1f, ratioCombinationSolution = 2,
            partsToRatio = 3, limitAnchor = 0.004f, limitHeight = 0.85f, smallCircleButtons = 0.3f;

    private int currentButton = 0;



    //creamos los diferentes array de botones distribuyendolos por zonas
    public Row(int pX, int pY, int pW, int pH, int nButtons, int index) {

        super(pX, pY, pW, pH);

        //creamos la posicion

        //dividimos la superficie
        int widthForButtons = (int) (width / partsToRatio * ratioCombinationSolution);
        int offset = (int) ((width - widthForButtons) / 2);

        //generamos el apoyo visual
        GenerateVisuals(offset, widthForButtons);

        //generamos los botones de solucion
        GenerateSolutionButtons(nButtons, posX + offset + widthForButtons, offset);

        //generamos los botones de combinacion
        combinationArray = new ButtonArray(posX + offset, posY, widthForButtons, height);
        combinationArray.GenerateButtons(nButtons, limitHeight, offsetButtons, smallCircleButtons);

        objectsInRow.addAll(combinationArray.GetButtons());
        objectsInRow.add((new Text("Night.ttf", posX + width/16, posY + height/3, width/15, height/15, String.valueOf(index), new ColorJ(0 ,0, 0))));

        //visuals.add(new VisualRectangle(posX + offset+widthForButtons, posY, offset, height, new ColorJ(128, 128, 128), true));
    }

    //limites y lineas que separan la fila
    void GenerateVisuals(int initialOffset, int widthForButtons) {

        VisualRectangle border =
                new VisualRectangle(posX, posY, width, height, new ColorJ(100, 200, 200, 200), new ColorJ("#000000"), true);
        objectsInRow.add(border);
        VisualRectangle limit1 =
                new VisualRectangle(posX + initialOffset,
                        posY + (int) (((1 - limitHeight) * height) / 2), (int) (width * limitAnchor), (int) (height * limitHeight), new ColorJ(128, 128, 128), true);
        objectsInRow.add(limit1);

        VisualRectangle limit2 =
                new VisualRectangle(posX + initialOffset + widthForButtons,
                        posY + (int) (((1 - limitHeight) * height) / 2), (int) (width * limitAnchor), (int) (height * limitHeight), new ColorJ(128, 128, 128), true);
        objectsInRow.add(limit2);

    }

    //botones de pista
    void GenerateSolutionButtons(int nButtons, int initialOffset, int widthForButtons) {
        int rows;
        int cols;

        //obtenemos raiz cuadrada
        int sqrt = (int) Math.sqrt(nButtons);

        //si es cuadrado perfecto se distribuye equitativamente
        if (sqrt * sqrt == nButtons) {
            rows = sqrt;
            cols = sqrt;
        }
        //Si no se prioriza la división horizontal
        else {
            cols = (int) Math.ceil(Math.sqrt(nButtons));
            rows = (int) Math.ceil((double) nButtons / cols);
        }


        // Calcular las dimensiones de cada parte
        int maxWidthPerButton = widthForButtons / cols;
        int maxHeightPerButton = height / rows;

        //obtenemos el tamaño de la "casilla del boton
        int buttonZone = maxWidthPerButton;
        //y la ajustamos a la altura si es necesario
        if (maxHeightPerButton < maxWidthPerButton)
            buttonZone = maxHeightPerButton;

        //obtenemos el tamaño del botton
        int buttonSize = (int) (buttonZone / offsetButtons) / 2;

        //offset inicial de x
        initialOffset += (int) ((buttonZone - buttonSize) / 2);

        //ajustamos el offset por si hay que añadir espacio extra, el /2 esta reicen añadido
        if (buttonZone * cols < widthForButtons)
            initialOffset += (int) (widthForButtons - buttonZone * cols) / 2;

        //offset vertical
        int initialOffsetY = posY;

        //y si de nuevo tenemos un offet vertical extra lo añadimos
        if (buttonZone * rows < height)
            initialOffsetY += (int) ((height - buttonZone * rows));

        //generamos los botones con los valores calculados
        for (int i = 0; i < nButtons; i++) {   //añadimos los botones
           SolutionButton b = new SolutionButton(initialOffset + (int) (buttonZone * (i % cols)), initialOffsetY + (int) (buttonZone * (i / cols) + buttonSize / 2),
                    buttonSize, buttonSize, 0.9f);
            solutionButtons.add(b);
            objectsInRow.add(b);
        }


    }


    //activamos un boton de la combinacion. Devolvemos si la fila ya esta completa
    public boolean Enablebutton(int n, ColorJ color, boolean inputEnabled, boolean clearColorEnable, boolean currentDaltonicEnable) {
        //obtenemos el primer boton disponible
        currentButton = combinationArray.GetFirstAvailableButton();
        //lo activamos
        combinationArray.Enablebutton(currentButton, color, n, 0.9f, inputEnabled, clearColorEnable, currentDaltonicEnable);
        //y ahora buscamos el proximo disponible
        currentButton = combinationArray.GetFirstAvailableButton();

        //devolvemos true o false segun si la fila esta completa
        if(currentButton == combinationArray.GetNButtons())
            return true;
        else
            return false;
    }

    //activamos un boton de la combinacion, en este caso con una imagen
    public boolean Enablebutton(int n, AndroidImage image, boolean inputEnabled, boolean clearColorEnable) {
        //obtenemos el primer boton disponible
        currentButton = combinationArray.GetFirstAvailableButton();
        //lo activamos
        combinationArray.Enablebutton(currentButton, image, n, 0.9f, inputEnabled, clearColorEnable);
        //y ahora buscamos el proximo disponible
        currentButton = combinationArray.GetFirstAvailableButton();

        //devolvemos true o false segun si la fila esta completa
        if(currentButton == combinationArray.GetNButtons())
            return true;
        else
            return false;
    }

    //muestra graficamente la pista combinacion - solucion
    public void SetSolution(ArrayList<Integer> solution) {
        for (int i = 0; i < solutionButtons.size(); i++) {
            solutionButtons.get(i).setSolution(solution.get(i));
        }

        //desactiva el input de los botones para impedir que se puedan eliminar una vez pasados de fila
        combinationArray.DisableInputButtons();
    }

    //devuelve el siguiente boton disponible para asignarle un color
    public int GetNextButton(){
        currentButton = combinationArray.GetFirstAvailableButton();
        return currentButton;
    }

    public void AddOffsetYToRow(int offsetY){
        for (GameObject o: objectsInRow) {
            o.posY += offsetY;
        }
    }

    public String GetButtonsCombination(){
        return combinationArray.GetButtonsCombination();
    }
}



