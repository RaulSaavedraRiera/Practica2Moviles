package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.ButtonArray;
import com.saavedradelariera.src.Buttons.AdButton;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.Buttons.ChangeToNewGameButton;
import com.saavedradelariera.src.Buttons.ShareButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.VisualRectangle;

import java.util.ArrayList;


//TODO limpiar escenas, como no haces publicos el cambiar de escena, meter SceneManager como update?
public class EndScene extends Scene {

    boolean win, daltonic;
    int tries, gameDifficult;
    ArrayList<ColorJ> colors;
    ArrayList<Integer> numbers;

    public EndScene(boolean win, int tries, ArrayList<ColorJ> colors, ArrayList<Integer> numbers, boolean daltonic, int gameDifficult) {
        this.win = win;
        this.tries = tries;
        this.colors = colors;
        this.numbers = numbers;
        this.daltonic = daltonic;
        this.gameDifficult = gameDifficult;

    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);

        new VisualRectangle(0, 0, graphics.GetWidth(), graphics.GetHeight(), ShopManager.getInstance().getBackgroundColor(), true);
        ColorJ buttonsColor = ShopManager.getInstance().getButtonsColor();

        if (win) {
            new Text("Night.ttf", 150, 120, 45, 125, "ENHORABUENA!!", new ColorJ(0, 0, 0));
            new Text("Night.ttf", 175, 175, 18, 50, "Has averiguado el código en:", new ColorJ(0, 0, 0));
            new Text("Night.ttf", 230, 250, 30, 70, String.valueOf(tries + 1) + " intentos", new ColorJ(0, 0, 0));

            new ShareButton(100, 510, 400, 100, buttonsColor, new ColorJ(0, 0, 0), "Night.ttf", 10);

            new Text("Night.ttf", 270, 300, 20, 50, "código:", new ColorJ(0, 0, 0));
            ButtonArray b = new ButtonArray(100, 350, 400, 100);
            b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, colors, false, false, daltonic);

            //Nivel se marca como pasado
            ProgressManager.getInstance().setLevelPass();

            GenericButton next = new GenericButton(100, 700, 400, 50, buttonsColor, new ColorJ(0, 0, 128), 10);
            new Text("Night.ttf", 180, 710, 36, 90, "Siguiente nivel", new ColorJ(0, 0, 0));

            next.setClickListener(new ClickListener() {
                @Override
                public void onClick() {
                    SceneManager.getInstance().useSceneStack();
                    WorldScene wS = new WorldScene();
                    SceneManager.getInstance().SetScene(wS);
                }
            });
        } else {
            new Text("Night.ttf", 200, 120, 45, 125, "GAME OVER", new ColorJ(0, 0, 0));
            new Text("Night.ttf", 175, 175, 18, 50, "Te has quedado sin intentos", new ColorJ(0, 0, 0));

            new AdButton(100, 510, 400, 100, buttonsColor, new ColorJ(0, 0, 0), "Night.ttf", 10);

            new ChangeToNewGameButton(100, 700, 400, 50, buttonsColor, new ColorJ(0, 0, 128), gameDifficult, 10, false);
            new Text("Night.ttf", 180, 710, 36, 90, "Volver a jugar", new ColorJ(0, 0, 0));
        }

        GenericButton finalButton = new GenericButton(100, 775, 400, 50, buttonsColor, new ColorJ(0, 0, 128), 10);
        new Text("Night.ttf", 260, 785, 36, 90, "Menu", new ColorJ(0, 0, 0));

        finalButton.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                SceneManager.getInstance().useSceneStack();
                MenuScene mS = new MenuScene();
                SceneManager.getInstance().SetScene(mS);
            }
        });
    }
}