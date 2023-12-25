package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeWorldButton;
import com.saavedradelariera.src.Buttons.LevelButton;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.ImageBackground;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.ResourcesManager;

public class WorldScene extends Scene {
    private int id;
    private int buttonWidth = 150;
    private int buttonHeight = 150;
    private int padding = 20;
    private int buttonsPerRow = 3;
    private int numberOfRows = 4;
    private int startX = 50;
    private int startY = 110;

    private ColorJ c = new ColorJ(128, 128, 128);
    private ColorJ c2 = new ColorJ(0, 0, 0);

    public WorldScene(){
        id = ResourcesManager.getInstance().getIdActualWorld();
    }

    @Override
    public void setScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.setScene(graphics, audioSystem);

        name = "WorldScene";

        new ColorBackground(ShopManager.getInstance().getBackgroundColor());
        AndroidImage i = ResourcesManager.getInstance().getBackground(graphics, false);
        if(i != null)
        {
            ImageBackground img = new ImageBackground(i);
        }


        Text t = new Text("Night.ttf",200, 50, 50, 100,  "MUNDO " + String.valueOf(id + 1),c2);
        ChangeWorldButton cw1 = new ChangeWorldButton("arrowC1.png", 400, 35, 50, 50, true);
        ChangeWorldButton cw2 = new ChangeWorldButton("arrowC2.png", 120, 35, 50, 50, false);
        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png",50, 35, 50,
                50, true);

        int contGlobal = 0;
        int n = ResourcesManager.getInstance().getLevelsInWorld(id);
        int numberOfRows = Math.min(n / buttonsPerRow + 1, 4);

        int row = 0;
        while (contGlobal < n && row < numberOfRows) {
            for (int col = 0; col < buttonsPerRow; col++) {
                int x = startX + col * (buttonWidth + padding);
                int y = startY + row * (buttonHeight + padding * 2);

                if (contGlobal < n) {
                    LevelButton l = new LevelButton(x, y, buttonWidth, buttonHeight, c, c2, contGlobal, "Night.ttf", "lock.png",10);
                    contGlobal++;
                } else {
                    break;
                }
            }
            row++;
        }
    }
}