package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.scenes.GameScene;

/*Botón de cambio de escena a la escena de selección de dificultad*/
public class ChangeToNewGameButton extends GenericButton {

    int n;
    boolean quick;

    public ChangeToNewGameButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, int n, int radius, boolean quick) {
        super(x, y, w, h, c, c2, radius);
        this.n = n;

        this.quick = quick;
    }

    @Override
    protected boolean HandleClick() {
        SceneManager.getInstance().useSceneStack();
        GameScene gS = new GameScene(n, quick, false);
        SceneManager.getInstance().SetScene(gS);
        return true;
    }
}
