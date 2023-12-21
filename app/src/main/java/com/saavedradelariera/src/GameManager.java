package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.messages.InputColorMessage;
import com.saavedradelariera.src.scenes.EndGameScene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*Clase GameManager que se encarga de gestionar el estado del juego en el nivel
* principal. Guarda los colores, las columnas, redirige el input a estas, etc.*/
public class GameManager extends GameObject {

    //private static GameManager instance = null;

    private ArrayList<Integer> playerTry = new ArrayList<Integer>();
    private ArrayList<Row> rows;

    Text triesT;

    private SolutionManager solutionManager;

    int currentRow = 0, buttonsPerRow, rowWidth, rowHeight, offsetX, offsetY, iniX, iniY;

    int difficult;

    int dragY = 0, totalYOffset = 0, maxYOffset = 0;



    ArrayList<AndroidImage> iconImages;
    //AndroidImage backgroundImage;
    private Random random = new Random();


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
    public void Init(int difficult, int solutionSize, int nRows, AndroidGraphics graphics, boolean load){

        //Lo registra para recibir mensajes
        SceneManager.getInstance().registerToMessage(this);

        //ImageButton background = new ImageButton(this.backgroundImage.getRoute(), 0,0, graphics.GetWidth(), graphics.GetHeight());

        //almacena las columnas y la dificultad, así como crea un solutionManager
        buttonsPerRow = solutionSize;
        GenerateRows(nRows, graphics);

        if(!load)
        {
            this.difficult = difficult;
            solutionManager = new SolutionManager(difficult, false);
        }
        else
        {
            this.difficult = ProgressManager.getInstance().getLevelInProgressDifficult();
            solutionManager = new SolutionManager(difficult, true);
        }


        //crea el array de la solucion que reutilizará
        playerTry = new ArrayList<>(solutionSize);
        for (int i = 0; i < solutionSize; i++)
            playerTry.add(0);

        triesT =   new Text("Night.ttf",205, 70, 20, 40,
                "Te quedan " + String.valueOf(rows.size() - currentRow) + " intentos", new ColorJ(0, 0, 0));

        if(load)
            LoadLevelState();
    }

    void GenerateRows(int nRows, AndroidGraphics graphics){
        rows = new ArrayList<>();

        //calcula diferentes valores para poder colocar los elementos correctamenrte
        iniX = (int)(graphics.GetWidthRelative()*0.025f);

        iniY = (int)(graphics.GetHeightRelative()*0.1f);

        rowWidth = (int)(graphics.GetWidthRelative()*0.95f);

        rowHeight = (int)((graphics.GetHeightRelative()*0.7f)/10);

        offsetX = 0;

        offsetY = height/10;

        for (int i = 0; i < nRows; i++) {
            Row row = new Row(iniX + offsetX*i, iniY + (rowHeight + offsetY)*i, rowWidth, rowHeight, buttonsPerRow , i+1);
            rows.add(row);
        }

       CalculateRowOffset();
    }

    public void setIconImages(ArrayList<AndroidImage> images)
    {
        this.iconImages = images;
    }

   /* public void setBackgroundImage(AndroidImage image)
    {
        this.backgroundImage = image;
    }*/
    
    public ColorJ GetColor(int i)
    {
        return colors.get(i);
    }

    public ArrayList<ColorJ> GetColors(){
        return colors;
    }

    public void AddRows(int n){
        for (int i = 1; i <= n; i++) {
            Row row = new Row(iniX + offsetX* rows.size(), iniY + (rowHeight + offsetY)*rows.size() , rowWidth, rowHeight, buttonsPerRow , rows.size());
            rows.add(row);
        }

        CalculateRowOffset();
    }

    void CalculateRowOffset(){
        if(rows.size() > 10)
            maxYOffset = iniY + (rowHeight + offsetY) * (rows.size() - 10);

    }

    public String GetLevelState(){
        String state = "";

        state+= String.valueOf(difficult);
        state+= solutionManager.getSolutionData();


        if(rows.size() < 10)
            state += "00" + String.valueOf(rows.size());
        else if(rows.size() < 100)
            state += "0" + String.valueOf(rows.size());
        else
            state += String.valueOf(rows.size());

        for (Row r: rows  ) {
            state += r.GetButtonsCombination();
        }

        if(state.trim().isEmpty())
            return "NONE";
        return state;
    }

    void LoadLevelState(){

        String state = ProgressManager.getInstance().getLevelRowState();
        if(state.length() < 3)
            return;

        int nRowsTarget = Integer.valueOf(state.substring(0 , 3));
        if(rows.size() != nRowsTarget)
            AddRows(nRowsTarget - rows.size());

        for (int i = 3; i < state.length(); i++) {
            ColorInput( Character.getNumericValue(state.charAt(i)));
        }

    }

    //Método para procesar el input de una nueva entrada a la solución dada por el jugador
    void ColorInput(int optionSelected){


        //si se ha alcanzado el máximo de botones no se hace anda
        if(currentRow >= rows.size())
        {
            return;
        }


        //añadimos intento
        playerTry.set(rows.get(currentRow).GetNextButton(), optionSelected);
        //System.out.println("nbuttons" + rows.get(currentRow).GetNextButton() + " " + "tries" + playerTry.size());

        boolean rowEnded;
        if(iconImages == null)
            rowEnded = rows.get(currentRow).Enablebutton(optionSelected, colors.get(optionSelected), inputInRows, clearInRows, currentDaltonicEnable);
        else
            rowEnded = rows.get(currentRow).Enablebutton(optionSelected, iconImages.get(optionSelected), inputInRows, clearInRows);

        //lo representamos graficamente, por defecto permitimos
        if(rowEnded)
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

        if(!win)
            SceneManager.getInstance().pushSceneStack();

        boolean type = false;
        if(difficult != 4)
            type = true;

        EndGameScene endGameScene = new EndGameScene(win, currentRow, colors, solutionManager.getSolution(), currentDaltonicEnable, difficult, type);
        SceneManager.getInstance().setScene(endGameScene);
    }

    @Override
    public boolean HandleInput(TouchEvent e) {

            if(e.getType() == TouchEvent.TouchEventType.DRAG) {
                int changeInY = (int)(e.getY() - dragY);
                //comprobar si puede bajar
                if((changeInY > 0 && totalYOffset + changeInY <= 0)
                        //o si puede subir
                        || (changeInY < 0 && totalYOffset + changeInY >= -maxYOffset))
                {
                    totalYOffset += changeInY;
                    ModifyRowsOffsetY((changeInY));
                }
                dragY = (int)e.getY();
                return true;
            }
            else if(e.getType() == TouchEvent.TouchEventType.TOUCH_DOWN)
            {
                dragY = (int)e.getY();
            }

            return false;

    }

    void ModifyRowsOffsetY(int currentOffsetY)
    {
        for (Row r: rows) {
            r.AddOffsetYToRow(currentOffsetY);
        }
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
            case "AcceleratorEvent":
                ColorInput(random.nextInt(solutionManager.getNTypes()));
                break;
        }
    }

    //Actualiza el texto al completar una fila
    void ActualiceText(){

        int tries =  rows.size() - currentRow;

        if(tries > 1)
            triesT.setText("Te quedan " + String.valueOf(tries) + " intentos");
        else
            triesT.setText("Es tu último intento!!");

    }
}