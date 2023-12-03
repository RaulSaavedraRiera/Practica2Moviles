package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.ChooseScene;
import com.saavedradelariera.src.scenes.MenuScene;

/*Botón de cambio de escena a la escena de selección de dificultad*/
public class ChangeSceneFinalButton extends GenericButton {

    public ChangeSceneFinalButton(int x, int y, int w, int h, ColorJ c, ColorJ c2) {
        super(x, y, w, h, c, c2);
    }

    @Override
    protected boolean HandleClick() {
        ChooseScene mS = new ChooseScene();
        SceneManager.getInstance().SetScene(mS);
        return true;
    }
}
