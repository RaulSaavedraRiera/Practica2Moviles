package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.VisualRectangle;

/*escena inicial */
public class MenuScene extends Scene {

    public MenuScene() {
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);
        name = "MenuScene";
        ColorJ buttonColor = ShopManager.getInstance().getButtonsColor();
        new ColorBackground(ShopManager.getInstance().getBackgroundColor());

        GenericButton buttonMenu = new GenericButton(100, 500, 400,
                100, buttonColor, new ColorJ(0, 0, 128), 10);
        GenericButton buttonWorld = new GenericButton(100, 700, 400,
                100, buttonColor, new ColorJ(0, 0, 128), 10);
        GenericButton buttonShop = new GenericButton(100, 900, 400,
                100, buttonColor, new ColorJ(0, 0, 128), 10);

        buttonMenu.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();

                Scene s;
                if(!ProgressManager.getInstance().levelInProgress() || ProgressManager.getInstance().WorldLevelInProgress())
                    s = new ChooseDifficultyScene();
                else
                    s = new GameScene(ProgressManager.getInstance().getLevelInProgressDifficult(), true, true);

                SceneManager.getInstance().SetScene(s);
            }
        });

        buttonWorld.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().pushSceneStack();
                Scene s;
                if(!ProgressManager.getInstance().levelInProgress() || !ProgressManager.getInstance().WorldLevelInProgress())
                {
                   s = new WorldScene();
                }
                else
                {
                    ResourcesManager.getInstance().setWorld(ProgressManager.getInstance().getWorldInProgress());
                    ResourcesManager.getInstance().getLevel(ProgressManager.getInstance().GetLevelInProgress());
                    ResourcesManager.getInstance().setIdActualLevel(ProgressManager.getInstance().GetLevelInProgress());
                    SceneManager.getInstance().pushSceneStack();

                    ProgressManager.getInstance().DeleteProgressInLevel();

                    s = new GameScene(4, false, true);
                }

                SceneManager.getInstance().SetScene(s);
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


        GenericButton bReset = new GenericButton(450, 30, 100,
                100, new ColorJ(220, 80, 80), new ColorJ (220, 80, 80), 10);
        Text t4 = new Text("Night.ttf", 460, 60, 30, 100, "RESET", new ColorJ(0, 0, 0));

        bReset.setClickListener(new ClickListener() {
            @Override
            public void onClick() {

                DeleteScene sS = new DeleteScene();
                SceneManager.getInstance().SetScene(sS);
            }
        });
        }
}
