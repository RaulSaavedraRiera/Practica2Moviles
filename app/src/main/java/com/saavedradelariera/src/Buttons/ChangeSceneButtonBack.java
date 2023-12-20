package com.saavedradelariera.src.Buttons;

import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.messages.ReleaseSoundMessage;
import com.saavedradelariera.src.scenes.MenuScene;
import com.saavedradelariera.src.scenes.WorldScene;


/*
* Botón de cambio de escena a la escena anterior, que debido a su gran uso es una clase propia
*/
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
        SceneManager.getInstance().resetStack();
        ResourcesManager.getInstance().resetWorld();
        ShopManager.getInstance().resetScene();
        MenuScene m = new MenuScene();
        SceneManager.getInstance().SetScene(m);
        return true;
    }
}
