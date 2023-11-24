package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.ShopScene;

public class ChangeSceneButtonShop extends ChangeSceneButton {
    public ChangeSceneButtonShop(int x, int y, int w, int h, ColorJ c, ColorJ c2) {
        super(x, y, w, h, c, c2);
    }

    @Override
    protected boolean HandleClick() {
        ShopScene sS = new ShopScene();
        SceneManager.getInstance().SetScene(sS);
        return true;
    }
}
