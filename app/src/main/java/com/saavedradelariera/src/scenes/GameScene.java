package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.DaltonicButton;
import com.saavedradelariera.src.GameManager;
import com.saavedradelariera.src.InputSolution;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.Row;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.VisualRectangle;

import java.util.ArrayList;

/*Escena de juego principal*/
public class GameScene extends Scene {
    int nButtons, nRows, nColors, difficult;

    ArrayList<AndroidImage> images;

    public GameScene(int diff){
        difficult = diff;
    }

    public GameScene(int diff, ArrayList<AndroidImage> levelImages)
    {
        difficult = diff;
        images = levelImages;
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {

        super.SetScene(graphics, audioSystem);

        images = ResourcesManager.getInstance().LoadImages(ResourcesManager.getInstance().getSkinsId(), graphics);


        SetSceneSettings(difficult);
        name = "GameScene";

        //le pasamos al manager las columnas para que las gestione
        GameManager gameManager = new GameManager();
        gameManager.Init(difficult, nButtons, nRows, graphics);

        if(images != null)
            gameManager.SetImages(images);


        InputSolution inputSolution;
        //creamos el input solution
        if(images == null)
            inputSolution = new InputSolution(0, (int)(graphics.GetHeightRelative()*0.9f),
                graphics.GetWidthRelative(), (int)(graphics.GetHeightRelative()*0.1f), nColors, gameManager.GetColors(), false);
        //o con sprites
        else
            inputSolution = new InputSolution(0, (int)(graphics.GetHeightRelative()*0.9f),
                    graphics.GetWidthRelative(), (int)(graphics.GetHeightRelative()*0.1f), nColors, images);


        //parte superior del nivel
        //rectangulo para tapar la parte de arriba
        new VisualRectangle(
                0, 0, (int)graphics.GetWidthRelative(), (int)(graphics.GetHeightRelative()*0.1f), new ColorJ(255,255,255), true);
         new Text("Night.ttf",200, 50, 25, 50,  "Averigua el código", new ColorJ(0, 0, 0));
        new ChangeSceneButtonBack("X.png", 70, 50, 30, 30);
        //si no hay imagenes metemos el daltonic button
        if(images == null)
            new DaltonicButton("ojo.png", 500, 40, 50, 50);

    }

    void SetSceneSettings(int difficult){
        switch(difficult)
        {
            case 0:
                nButtons = 4;
                nRows = 6;
                nColors = 4;
                break;
            case 1:
                nButtons = 4;
                nRows = 8;
                nColors = 6;
                break;
            case 2:
                nButtons = 5;
                nRows = 10;
                nColors = 8;
                break;
            case 3:
                nButtons = 6;
                nRows = 10;
                nColors = 9;
                break;
                //Nivel personalizado

            case 4:
                Level aux = ResourcesManager.getInstance().getActualLevel();
                nButtons = aux.getCodeSize();
                nRows = aux.getAttempts();
                nColors = aux.getCodeOpt();
                break;
        }

    }
}