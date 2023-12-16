package com.saavedradelariera.src.Buttons;

import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.messages.ReleaseSoundMessage;
import com.saavedradelariera.src.scenes.MenuScene;
import com.saavedradelariera.src.scenes.WorldScene;


/*Botón de cambio de escena a la escena anterior*/
public class ChangeSceneButtonBack extends ImageButton {


    private boolean isWorld = false;
    public ChangeSceneButtonBack(String route, int x, int y, int w, int h ){
        super(route,x,y,w,h);
    }

    public ChangeSceneButtonBack(String route, int x, int y, int w, int h, boolean isWorld){
        super(route,x,y,w,h);
        this.isWorld = isWorld;
    }

    @Override
    protected boolean HandleClick() {
        if(!isWorld)
        {
            SceneManager.getInstance().SendMessageToActiveScene(new ReleaseSoundMessage());
            SceneManager.getInstance().SetScene(SceneManager.getInstance().useSceneStack());

            ProgressManager.getInstance().resetGame();
        }else {
            SceneManager.getInstance().resetStack();
            ResourcesManager.getInstance().resetWorld();
            MenuScene m = new MenuScene();
            SceneManager.getInstance().SetScene(m);
        }
        return true;
    }
}
