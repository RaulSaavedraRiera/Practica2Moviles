package com.practica1.src.Buttons;

import com.practica1.src.SceneManager;
import com.practica1.graphics.ColorJ;
import com.practica1.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class ChangeSceneButtonGame extends ChangeSceneButton {

    int n;
    IEngine engine;
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
