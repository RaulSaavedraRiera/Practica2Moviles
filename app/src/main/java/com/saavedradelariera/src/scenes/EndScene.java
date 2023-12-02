package com.saavedradelariera.src.scenes;
import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.ButtonArray;
import com.saavedradelariera.src.Buttons.ChangeSceneButton;
import com.saavedradelariera.src.Buttons.ChangeToNewGameButton;
import com.saavedradelariera.src.Buttons.ShareButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;
import java.util.ArrayList;


//TODO limpiar escenas, como no haces publicos el cambiar de escena, meter SceneManager como update?
public class EndScene extends Scene {

    boolean win;
    int tries, gameDifficult;
    ArrayList<ColorJ> colors;
    ArrayList<Integer> numbers;
    boolean daltonic;


    public EndScene(boolean win, int tries, ArrayList<ColorJ> colors, ArrayList<Integer> numbers, boolean daltonic, int gameDifficult){
        this.win =win;
        this.tries = tries;
        this.colors = colors;
        this.numbers = numbers;
        this.daltonic = daltonic;
        this.gameDifficult = gameDifficult;

    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);

        if(win)
        {
            new Text("Night.ttf",150, 120, 45, 125,  "ENHORABUENA!!", new ColorJ(0, 0, 0));
            new Text("Night.ttf",175, 175, 18, 50,  "Has averiguado el código en:", new ColorJ(0, 0, 0));
            new Text("Night.ttf",230, 250, 30, 70,  String.valueOf(tries) + " intentos", new ColorJ(0, 0, 0));

            new ShareButton(100, 510, 400, 100, new ColorJ(0, 255, 255), new ColorJ(0, 0, 0), "Night.ttf", 10);
        }
        else
        {
            new Text("Night.ttf",200, 120, 45, 125,  "GAME OVER", new ColorJ(0, 0, 0));
            new Text("Night.ttf",175, 175, 18, 50,  "Te has quedado sin intentos", new ColorJ(0, 0, 0));
        }

        new Text("Night.ttf",270, 300, 20, 50,  "código:", new ColorJ(0, 0, 0));
        ButtonArray b =  new ButtonArray(100, 350, 400, 100);
        b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, colors, false, false, daltonic);

        new ChangeToNewGameButton(100, 700, 400, 50, new ColorJ(0, 255, 255), new ColorJ(0, 0, 128), gameDifficult, 10);
        new Text("Night.ttf",180, 710, 36, 90,  "Volver a jugar", new ColorJ(0, 0, 0));

        ChangeSceneButton finalButton = new ChangeSceneButton(100, 775, 400, 50, new ColorJ(0, 255, 255), new ColorJ(0, 0, 128), 10);
        new Text("Night.ttf",160, 785, 36, 90,  "Elegir dificultad", new ColorJ(0, 0, 0));
        finalButton.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                ChooseScene mS = new ChooseScene();
                SceneManager.getInstance().SetScene(mS);
            }
        });
    }
}
