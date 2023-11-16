package com.practica1.src.Buttons;

import com.practica1.src.SceneManager;
import com.practica1.input.TouchEvent;
import com.practica1.src.messages.ReleaseSoundMessage;
import com.practica1.src.scenes.MenuScene;


/*Botón de cambio de escena a la escena inicial*/
public class ChangeSceneButtonBack extends ImageButton {


    public ChangeSceneButtonBack(String route, int x, int y, int w, int h ){
        super(route,x,y,w,h);
    }

    @Override
    protected boolean HandleClick() {
        MenuScene mS = new MenuScene();
        SceneManager.getInstance().SendMessageToActiveScene(new ReleaseSoundMessage());
        SceneManager.getInstance().SetScene(mS);
        return true;
    }
}
