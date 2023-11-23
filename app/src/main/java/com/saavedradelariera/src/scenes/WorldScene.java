package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeWorldButton;
import com.saavedradelariera.src.Buttons.LevelButton;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.ResourcesManager;

public class WorldScene extends Scene {


    private int id;
    private int buttonWidth = 150;
    private int buttonHeight = 150;
    private int padding = 20;
    private int buttonsPerRow = 3;
    //TODO: dependerá del numero de elemetos
    private int numberOfRows = 4;
    private int startX = 50;
    private int startY = 110;

    private ColorJ c = new ColorJ(0, 255, 255);
    private ColorJ c2 = new ColorJ(0, 0, 0);

    public WorldScene(){
        id = ResourcesManager.getInstance().getIdActualWordl();
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);

        name = "WorldScene";

        Text t = new Text("Night.ttf",200, 50, 50, 100,  "MUNDO " + String.valueOf(id), new ColorJ(0, 0, 0));
        ChangeWorldButton cw1 = new ChangeWorldButton("arrowC1.png", 400, 35, 50, 50, true);
        ChangeWorldButton cw2 = new ChangeWorldButton("arrowC2.png", 120, 35, 50, 50, false);
        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png",50, 35, 50,
                50);

        int contGlobal = 0;
        int n = ResourcesManager.getInstance().getLevelInWorld(id - 1);
        int numberOfRows = Math.min(n / buttonsPerRow + 1, 4);


        int row = 0;
        while (contGlobal < n && row < numberOfRows) {
            for (int col = 0; col < buttonsPerRow; col++) {
                int x = startX + col * (buttonWidth + padding);
                int y = startY + row * (buttonHeight + padding * 2);

                if (contGlobal < n) {
                    LevelButton l = new LevelButton(x, y, buttonWidth, buttonHeight, c, c2, contGlobal + 1, "Night.ttf");
                    contGlobal++;
                } else {
                    break;
                }
            }
            row++;
        }
    }
}