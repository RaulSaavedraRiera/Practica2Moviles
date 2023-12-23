package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;

/*Escena de selección de dificultad*/
public class ChooseDifficultyScene extends Scene {

    public ChooseDifficultyScene() {
    }

    @Override
    public void setScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.setScene(graphics, audioSystem);

        name = "ChooseScene";

        new ColorBackground(ShopManager.getInstance().getBackgroundColor());

        Text tT = new Text("Night.ttf", 75, 250, 30, 100, "¿En que dificultad quieres jugar?", new ColorJ(0, 0, 0));

        //segun que boton se pulse se le darán unos calores de inicialización diferentes al game

        GenericButton b = new GenericButton(135, 320, 300, 75, new ColorJ(0, 230, 0), new ColorJ(0, 0, 0), 10);
        Text t = new Text("Night.ttf", 240, 355, 40, 100, "Fácil", new ColorJ(0, 0, 0));
        b.setClickListener(createClickListener(0));

        GenericButton b1 = new GenericButton(135, 410, 300, 75, new ColorJ(255, 255, 0), new ColorJ(0, 0, 0), 10);
        Text t1 = new Text("Night.ttf", 235, 440, 40, 100, "Medio", new ColorJ(0, 0, 0));
        b1.setClickListener(createClickListener(1));

        GenericButton b2 = new GenericButton(135, 500, 300, 75, new ColorJ(255, 165, 0), new ColorJ(0, 0, 0), 10);
        Text t2 = new Text("Night.ttf", 235, 530, 40, 100, "Dificil", new ColorJ(0, 0, 0));
        b2.setClickListener(createClickListener(2));

        GenericButton b3 = new GenericButton(135, 590, 300, 75, new ColorJ(170, 0, 0), new ColorJ(0, 0, 0), 10);
        Text t3 = new Text("Night.ttf", 200, 620, 40, 100, "Imposible", new ColorJ(0, 0, 0));
        b3.setClickListener(createClickListener(3));

        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png", 75, 75, 30, 30, true);
    }


    ClickListener createClickListener(int n) {
        return new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();
                GameScene gS = new GameScene(n, true, false);
                SceneManager.getInstance().setScene(gS);

            }
        };
    }
}
