package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.WorldManager;
import com.saavedradelariera.src.scenes.ChooseScene;
import com.saavedradelariera.src.scenes.WorldScene;

/*Botón de cambio de escena a la escena de selección de dificultad*/
public class ChangeSceneButtonWorld extends ChangeSceneButton {

    public ChangeSceneButtonWorld(int x, int y, int w, int h, ColorJ c, ColorJ c2){
        super(x,y,w,h,c,c2);
    }

    @Override
    protected boolean HandleClick() {
        WorldScene wS = new WorldScene();
        WorldManager.getInstance().addScene(wS);
        SceneManager.getInstance().SetScene(wS);
        return true;
    }
}
