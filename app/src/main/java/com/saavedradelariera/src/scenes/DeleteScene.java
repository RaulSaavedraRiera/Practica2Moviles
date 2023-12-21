package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;

/*Escena de selección de dificultad*/
public class DeleteScene extends Scene {

    public DeleteScene(){
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);
        new ColorBackground(ShopManager.getInstance().getBackgroundColor());

        name = "DeleteScene";

        Text tT = new Text("Night.ttf", 30, 250, 30, 100,  "¿Seguro que quieres borrar los datos?", new ColorJ(0, 0, 0));


        GenericButton yesB = new GenericButton(135, 320, 300,
                75, new ColorJ(220, 80, 80),new ColorJ(0, 0, 0), 10);
        GenericButton noB = new GenericButton(135, 500, 300,
                75, new ColorJ(80, 150, 80),new ColorJ(0, 0, 0), 10);

        Text t2 = new Text("Night.ttf", 260, 340, 50, 100, "SI", new ColorJ(0, 0, 0));
        Text t3 = new Text("Night.ttf", 260, 520, 50, 100, "NO", new ColorJ(0, 0, 0));


        yesB.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                ProgressManager.getInstance().resetGame();
                ShopManager.getInstance().eraseData();

                MenuScene mS = new MenuScene();
                SceneManager.getInstance().setScene(mS);
            }
        });

        noB.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                MenuScene mS = new MenuScene();
                SceneManager.getInstance().setScene(mS);
            }
        });

    }
}
