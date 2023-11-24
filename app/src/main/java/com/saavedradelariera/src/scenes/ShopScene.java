package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeWorldButton;
import com.saavedradelariera.src.Buttons.ImageButton;
import com.saavedradelariera.src.Buttons.LevelButton;
import com.saavedradelariera.src.Buttons.SkinButton;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Skin;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.WorldManager;

public class ShopScene extends Scene {
    private int buttonWidth = 150;
    private int buttonHeight = 150;
    private int startX = 50;
    private int startY = 110;
    private int padding = 20;
    private ColorJ c = new ColorJ(0, 255, 255);
    private ColorJ blackColor = new ColorJ(0, 0, 0);
    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio androidAudio) {
        super.SetScene(graphics, androidAudio);

        name = "CustomizeScene";

        //Text t = new Text("Night.ttf",200, 50, 50, 100,  String.valueOf(id), new ColorJ(0, 0, 0));
        int buttonsPerRow = 3;
        int contGlobal = 0;
        int n = ShopManager.getInstance().getBackgrounds().size();
        int numberOfRows = Math.min(n / buttonsPerRow + 1, 4);


        int row = 0;
        while (contGlobal < n && row < numberOfRows) {
            for (int col = 0; col < buttonsPerRow; col++) {
                int x = startX + col * (buttonWidth + padding);
                int y = startY + row * (buttonHeight + padding * 2);

                if (contGlobal < n) {
                    Skin skin = ShopManager.getInstance().getBackgrounds().get(contGlobal);
                    //SkinButton skinButton = new SkinButton(x, y, buttonWidth, buttonHeight, c, c2, contGlobal + 1, "Night.ttf", bg.getPath());
                    ImageButton imageButton = new ImageButton(skin.getPath(), x, y, buttonWidth, buttonHeight);
                    Text price = new Text("Night.ttf", x+15, y+180, buttonWidth/2,buttonHeight/2,""+skin.getPrice(),blackColor);
                    contGlobal++;
                } else {
                    break;
                }
            }
            row++;
        }

    }
}
