package com.saavedradelariera.src.Buttons;

import android.graphics.Bitmap;

import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.mobileManagers.MobileShare;
import com.practica1.androidengine.mobileManagers.ScreenShootFinish;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class ShareButton extends ChangeSceneButton {

    public ShareButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, String font, int radius){
        super(x,y,w,h,c,c2, radius);

        Text t = new Text(font,x + w/5, y +h/4, w/7, h/7, "Compartir", c2);
    }

    @Override
    protected boolean HandleClick() {

        SceneManager.getInstance().getEngine().GetGraphics().generateScreenShoot(0
                , 0, SceneManager.getInstance().getEngine().GetGraphics().GetWidth(), SceneManager.getInstance().getEngine().GetGraphics().GetHeight() / 2, new ScreenShootFinish() {
                    @Override
                    public void doAction(Bitmap bitmap) {
                        SceneManager.getInstance().getEngine().SolicitateShare(bitmap, "ME HE PASADO EL MASTERMIND");
                    }
                });


        return true;
    }
}
