package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.DaltonicButton;
import com.saavedradelariera.src.GameManager;
import com.saavedradelariera.src.InputSolution;
import com.saavedradelariera.src.Row;
import com.saavedradelariera.src.Text;

import java.util.ArrayList;

/*Escena de juego principal*/
public class GameScene extends Scene {
    int nButtons, nRows, nColors, difficult;
    boolean scaleRowWithSpace = false;

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


        SetSceneSettings(difficult);
        name = "GameScene";


        //calcula diferentes valores para poder colocar los elementos correctamenrte
        int x = (int)(graphics.GetWidthRelative()*0.025f);

        int y = (int)(graphics.GetHeightRelative()*0.1f);

        int width = (int)(graphics.GetWidthRelative()*0.95f);

        int height;
        if(scaleRowWithSpace)
            height = (int)((graphics.GetHeightRelative()*0.7f)/nRows);
        else
            height = (int)((graphics.GetHeightRelative()*0.7f)/10);

        int offsetX = 0;

        int offsetY;

        if(scaleRowWithSpace)
            offsetY= (int)(y/nRows);
        else
            offsetY = height/10;



        //genera las columnas
        ArrayList<Row> rows = new ArrayList<>();

        for (int i = 0; i < nRows; i++) {
            Row row = new Row(x + offsetX*i, y + height*i + offsetY*i, width, height, nButtons, i+1);
            rows.add(row);
        }

        //le pasamos al manager las columnas para que las gestione
        GameManager gameManager = new GameManager();
        gameManager.Init(difficult, rows, nButtons);

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

        new Text("Night.ttf",200, 50, 25, 50,  "Averigua el código", new ColorJ(0, 0, 0));
        new DaltonicButton("ojo.png", 500, 40, 50, 50);
        new ChangeSceneButtonBack("X.png", 70, 50, 30, 30);

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

                break;
        }

    }
}