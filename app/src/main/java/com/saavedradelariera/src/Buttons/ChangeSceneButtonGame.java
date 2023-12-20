package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class ChangeSceneButtonGame extends GenericButton {

    int n;
    AndroidEngine engine;

    public ChangeSceneButtonGame(int x, int y, int w, int h, ColorJ c, ColorJ c2, int n, int radius) {
        super(x, y, w, h, c, c2, radius);

        this.n = n;
    }

    @Override
    protected boolean HandleClick() {
        SceneManager.getInstance().pushSceneStack();

        GameScene gS = new GameScene(n, true, false);
        SceneManager.getInstance().SetScene(gS);

        return true;
    }
}
