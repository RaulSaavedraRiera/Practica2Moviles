package com.saavedradelariera.src;

import com.saavedradelariera.src.messages.Message;
import com.practica1.graphics.ColorJ;
import com.saavedradelariera.src.messages.InputColorMessage;
import com.saavedradelariera.src.messages.ReleaseSoundMessage;
import com.saavedradelariera.src.scenes.EndScene;

import java.util.ArrayList;
import java.util.Arrays;

/*Clase GameManager que se encarga de gestionar el estado del juego en el nivel
* principal. Guarda los colores, las columnas, redirige el input a estas, etc.*/
public class GameManager extends GameObject {

    //private static GameManager instance = null;

    private ArrayList<Integer> playerTry = new ArrayList<Integer>();
    private ArrayList<Row> rows;

    Text triesT;

    private SolutionManager solutionManager;

    int currentRow = 0;

    int diffcult;


    //si activamos inputInRows y no clearInRows podras seleccionar colores desde las filas pero no quitarlos
    boolean currentDaltonicEnable = false,
        inputInRows = true, clearInRows = true;

    //array fijo de colores común a todas las dificultades
    final ArrayList<ColorJ> colors = new ArrayList<>(Arrays.asList(
            new ColorJ(255, 0, 0),     // Rojo
            new ColorJ(255, 165, 0),   // Naranja
            new ColorJ(255, 255, 0),   // Amarillo
            new ColorJ(0, 128, 0),     // Verde
            new ColorJ(0, 255, 255),   // Celeste
            new ColorJ(0, 0, 255),     // Azul
            new ColorJ(128, 0, 128),   // Morado
            new ColorJ(255, 192, 203),  // Rosa
            new ColorJ(210, 180, 140)// Marrón
    ));

    public GameManager() {
        super(0,0,0,0);
    }

  //Inicializa el GameManager
    public void Init(int difficult, ArrayList<Row> r, int solutionSize){

        //Lo registra para recibir mensajes
        SceneManager.getInstance().RegisterToMessage(this);


        //almacena las columnas y la dificultad, así como crea un solutionManager
        rows = r;
        this.diffcult = difficult;

        solutionManager = new SolutionManager(difficult);

        //crea el array de la solucion que reutilizará
        playerTry = new ArrayList<>(solutionSize);
        for (int i = 0; i < solutionSize; i++)
            playerTry.add(0);


        triesT =   new Text("Night.ttf",205, 70, 20, 40,
                "Te quedan " + String.valueOf(rows.size() - currentRow) + " intentos", new ColorJ(0, 0, 0));


    }


    public ColorJ GetColor(int i)
    {
        return colors.get(i);
    }

    public ArrayList<ColorJ> GetColors(){
        return colors;
    }

    //Método para procesar el input de una nueva entrada a la solución dada por el jugador
    void ColorInput(int colorSelected){


        //si se ha alcanzado el máximo de botones no se hace anda
        if(currentRow >= rows.size())
        {
            return;
        }


        //añadimos intento
        playerTry.set(rows.get(currentRow).GetNextButton(), colorSelected);
        //System.out.println("nbuttons" + rows.get(currentRow).GetNextButton() + " " + "tries" + playerTry.size());



        //lo representamos graficamente, por defecto permitimis
        if(rows.get(currentRow).Enablebutton(colorSelected, colors.get(colorSelected), inputInRows, clearInRows, currentDaltonicEnable))
        {
            //si se ha llegado al final de linea
            ArrayList<Integer> compSol = solutionManager.compareSolution(playerTry);

            //si ha ganado
            if(compSol.get(compSol.size()-1) == 99)
            {
                //lo quitamos y ganamos
                compSol.remove(compSol.size()-1);
                EndGame(true);
            }
            else if(currentRow >= rows.size()-1)
                EndGame(false);

            //enviamos la solucion para ser represnetada
            rows.get(currentRow).SetSolution(compSol);

            currentRow++;
            
           ActualiceText();

        }
    }

    //al acabar el juego cargamos la siguiente escena con los valores según si ha ganado, dificultad seleccionada.
    private void EndGame(boolean win){


        EndScene endScene = new EndScene(win, currentRow, colors, solutionManager.getSolution(), currentDaltonicEnable, diffcult);
        SceneManager.getInstance().SendMessageToActiveScene(new ReleaseSoundMessage());
        SceneManager.getInstance().SetScene(endScene);
    }

    //Recibe mensajes tanto de input como de cambio en el modo daltonico
    @Override
    public void ReceiveMessage(Message m) {
        switch(m.id)
        {
            case "InputColor":
                InputColorMessage iM = (InputColorMessage) m;
                ColorInput(iM.nButton);
                break;
            case "DaltonicChangeSolicitate":
                currentDaltonicEnable = !currentDaltonicEnable;
                break;
        }
    }

    //Actualiza el texto al completar una fila
    void ActualiceText(){

        int tries =  rows.size() - currentRow;

        if(tries > 1)
            triesT.SetText("Te quedan " + String.valueOf(tries) + " intentos");
        else
            triesT.SetText("Es tu último intento!!");

    }
}