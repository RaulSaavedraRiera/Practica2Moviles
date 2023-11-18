package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.ChooseScene;

/*Botón de cambio de escena a la escena de selección de dificultad*/
public class ChangeSceneButtonMenu extends ChangeSceneButton {

    public ChangeSceneButtonMenu(int x, int y, int w, int h, ColorJ c, ColorJ c2){
        super(x,y,w,h,c,c2);
    }

    @Override
    protected boolean HandleClick() {
        ChooseScene cS = new ChooseScene();
        SceneManager.getInstance().SetScene(cS);
        return true;
    }
}
