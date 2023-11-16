package com.practica1.src.Buttons;

import com.practica1.src.SceneManager;
import com.practica1.graphics.ColorJ;
import com.practica1.src.scenes.ChooseScene;

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
