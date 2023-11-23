package com.saavedradelariera.src.Buttons;

import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.messages.ReleaseSoundMessage;
import com.saavedradelariera.src.scenes.MenuScene;


/*Botón de cambio de escena a la escena anterior*/
public class ChangeSceneButtonBack extends ImageButton {


    public ChangeSceneButtonBack(String route, int x, int y, int w, int h ){
        super(route,x,y,w,h);
    }

    @Override
    protected boolean HandleClick() {
        SceneManager.getInstance().SendMessageToActiveScene(new ReleaseSoundMessage());
        SceneManager.getInstance().SetScene(SceneManager.getInstance().useSceneStack());
        return true;
    }
}
