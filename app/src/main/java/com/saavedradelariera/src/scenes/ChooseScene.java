package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonGame;
import com.saavedradelariera.src.Text;

/*Escena de selección de dificultad*/
public class ChooseScene extends Scene {

    public ChooseScene(){
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);

        name = "ChooseScene";

        Text tT = new Text("Night.ttf", 75, 250, 30, 100,  "¿En que dificultad quieres jugar?", new ColorJ(0, 0, 0));


        //segun que boton se pulse se le darán unos calores de inicialización diferentes al game

        ChangeSceneButtonGame b = new ChangeSceneButtonGame(135, 320, 300,
                75, new ColorJ(0, 230, 0), new ColorJ(0, 0, 0), 0, 10);
        Text t = new Text("Night.ttf", 240, 355, 40, 100,  "Fácil", new ColorJ(0, 0, 0));

        ChangeSceneButtonGame b1= new ChangeSceneButtonGame(135, 410, 300,
                75, new ColorJ(255, 255, 0), new ColorJ(0, 0, 0), 1, 10);
        Text t1 = new Text("Night.ttf",235, 440, 40, 100,  "Medio", new ColorJ(0, 0, 0));

        ChangeSceneButtonGame b2 = new ChangeSceneButtonGame(135, 500, 300,
                75, new ColorJ(255, 165, 0), new ColorJ(0, 0, 0), 2, 10);
        Text t2 = new Text("Night.ttf",235, 530, 40, 100,  "Dificil", new ColorJ(0, 0, 0));

        ChangeSceneButtonGame b3 = new ChangeSceneButtonGame(135, 590, 300,
                75,  new ColorJ(170, 0, 0), new ColorJ(0, 0, 0), 3, 10);
        Text t3 = new Text("Night.ttf",200, 620, 40, 100,  "Imposible", new ColorJ(0, 0, 0));

        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png",75, 75, 30,
                30);
    }
}
