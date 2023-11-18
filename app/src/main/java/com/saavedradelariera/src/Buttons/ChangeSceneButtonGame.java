package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class ChangeSceneButtonGame extends ChangeSceneButton {

    int n;
    AndroidEngine engine;
    public ChangeSceneButtonGame(int x, int y, int w, int h, ColorJ c, ColorJ c2, int n){
        super(x,y,w,h,c,c2);

        this.n = n;
    }

    @Override
    protected boolean HandleClick() {
        GameScene gS = new GameScene(n);
        SceneManager.getInstance().SetScene(gS);

        return true;
    }
}
