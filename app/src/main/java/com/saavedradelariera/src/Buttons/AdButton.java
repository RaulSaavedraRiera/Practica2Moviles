package com.saavedradelariera.src.Buttons;

import android.graphics.Bitmap;

import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.mobileManagers.AdFinish;
import com.practica1.androidengine.mobileManagers.ScreenShootFinish;
import com.saavedradelariera.src.GameManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class AdButton extends GenericButton {

    public AdButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, String font, int radius){
        super(x,y,w,h,c,c2, radius);

        Text t = new Text(font,x + w/10, y +h/4, w/7, h/7, "+ 2 intentos", c2);
    }

    @Override
    protected boolean HandleClick() {

        /*SceneManager.getInstance().getEngine().SolicitateRewardAd(new AdFinish() {
            @Override
            public void doAction() {


            }
        });*/
        GameScene gS = (GameScene) SceneManager.getInstance().getPeckStack();

        SceneManager.getInstance().useSceneStack();
        SceneManager.getInstance().ReturnToScene(gS);

        gS.getGameManager().AddRows(2);


        return true;
    }
}
