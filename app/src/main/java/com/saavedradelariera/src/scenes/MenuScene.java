package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;

/*escena inicial */
public class MenuScene extends Scene {

    public MenuScene() {
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);
        name = "MenuScene";

        GenericButton buttonMenu = new GenericButton(100, 500, 400,
                100, new ColorJ(0, 255, 255), new ColorJ(0, 0, 128), 10);
        GenericButton buttonWorld = new GenericButton(100, 700, 400,
                100, new ColorJ(0, 210, 180), new ColorJ(0, 0, 128), 10);
        GenericButton buttonShop = new GenericButton(100, 900, 400,
                100, new ColorJ(0, 210, 180), new ColorJ(0, 0, 128), 10);

        buttonMenu.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();
                ChooseScene cS = new ChooseScene();
                SceneManager.getInstance().SetScene(cS);
            }
        });

        buttonWorld.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();
                WorldScene wS = new WorldScene();
                SceneManager.getInstance().SetScene(wS);
            }
        });

        buttonShop.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();
                ShopScene sS = new ShopScene();
                SceneManager.getInstance().SetScene(sS);
            }
        });

        Text t1 = new Text("Spicy.ttf", 125, 250, 60, 100, "Master Mind", new ColorJ(0, 0, 0));
        Text t = new Text("Night.ttf", 230, 535, 50, 100, "JUGAR", new ColorJ(0, 0, 0));
        Text t2 = new Text("Night.ttf", 215, 735, 50, 100, "MUNDOS", new ColorJ(0, 0, 0));
        Text t3 = new Text("Night.ttf", 145, 935, 50, 100, "PERSONALIZAR", new ColorJ(0, 0, 0));

    }
}
