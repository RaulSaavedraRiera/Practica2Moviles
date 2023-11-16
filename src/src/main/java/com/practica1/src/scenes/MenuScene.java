package com.practica1.src.scenes;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;
import com.practica1.sound.IAudioSystem;
import com.practica1.src.Buttons.ChangeSceneButton;
import com.practica1.src.Buttons.ChangeSceneButtonMenu;
import com.practica1.src.Text;

/*escena inicial */
public class MenuScene extends Scene {

    public MenuScene(){
    }
    @Override
    public void SetScene(IGraphics graphics, IAudioSystem audioSystem) {

        super.SetScene(graphics, audioSystem);

        name = "MenuScene";

        ChangeSceneButton b = new ChangeSceneButtonMenu(100, 500, 400,
                100, new ColorJ(0, 255, 255), new ColorJ(0, 0, 128));
        Text t = new Text("Night.ttf",230, 535, 50, 100,  "JUGAR", new ColorJ(0, 0, 0));
        Text t1 = new Text("Spicy.ttf",125, 250, 60, 100,  "Master Mind", new ColorJ(0, 0, 0));
    }
}
