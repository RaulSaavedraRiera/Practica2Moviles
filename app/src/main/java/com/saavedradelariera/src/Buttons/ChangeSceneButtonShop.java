package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.ShopScene;

public class ChangeSceneButtonShop extends GenericButton {
    public ChangeSceneButtonShop(int x, int y, int w, int h, ColorJ c, ColorJ c2, int radius) {
        super(x, y, w, h, c, c2, radius);
    }

    @Override
    protected boolean HandleClick() {
        SceneManager.getInstance().pushSceneStack();

        ShopScene sS = new ShopScene();
        SceneManager.getInstance().SetScene(sS);
        return true;
    }
}
