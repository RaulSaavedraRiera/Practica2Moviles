package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class LevelButton extends ChangeSceneButton {

    int id;

    public LevelButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, int id, String font){
        super(x,y,w,h,c,c2);

        Text t = new Text(font,posX + width/3, posY +height/3, w/3, height/3, String.valueOf(id), c2);
    }

    @Override
    protected boolean HandleClick() {
        Level level = ResourcesManager.getInstance().getLevel(id);
        SceneManager.getInstance().pushSceneStack();

        GameScene gS = new GameScene(4);
        SceneManager.getInstance().SetScene(gS);

        return true;
    }
}
