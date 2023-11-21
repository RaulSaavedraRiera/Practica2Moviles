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

        int id;
        if(WorldManager.getInstance().changeWorld(this.type))
        {
            id = WorldManager.getInstance().getIdActualWordl();
            if(WorldManager.getInstance().isSceneCreated(id))
            {
                SceneManager.getInstance().SetScene(WorldManager.getInstance().getWorldScene());
            }
            else {
                WorldScene ws = new WorldScene();
                WorldManager.getInstance().addScene(ws);
                SceneManager.getInstance().SetScene(ws);

            }
        }

        return true;
    }
}
