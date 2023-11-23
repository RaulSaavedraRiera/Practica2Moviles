package com.saavedradelariera.src.Buttons;

import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.WorldManager;
import com.saavedradelariera.src.scenes.WorldScene;

/*Botón de daltonismo; cuenta con una imagen fija y si es pulsado manda un mensaje de cambio de dicho modo*/
public class ChangeWorldButton extends ImageButton {

    boolean type;

    public ChangeWorldButton(String route, int x, int y, int w, int h, boolean type){
        super(route, x,y,w,h);

        this.type = type;
    }

    @Override
    protected boolean HandleClick() {

        if(WorldManager.getInstance().changeWorld(this.type))
        {
            if(this.type){

                SceneManager.getInstance().pushSceneStack();
                WorldScene ws = new WorldScene();
                SceneManager.getInstance().SetScene(ws);
            }else{
                SceneManager.getInstance().SetScene(SceneManager.getInstance().useSceneStack());
            }

        }

        return true;
    }
}
