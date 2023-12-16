package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidFont;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.scenes.GameScene;


/*Botón de cambio de escena a la escena de juego, se almacena la dificultad para pasarselo a esta*/
public class LevelButton extends GenericButton {

    private int id;
    private ColorJ c, c2;
    private int x, y, w, h;
    private int radius;
    private String routeF;
    private String routeI;

    private boolean pass;

    private AndroidFont font;

    ProgressManager pM;


    public LevelButton(int x, int y, int w, int h, ColorJ c, ColorJ c2, int id, String routeF, String routeI, int radius){
        super(x,y,w,h,c,c2, radius);

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.c = c;
        this.c2 = c2;
        this.routeF = routeF;
        this.radius = radius;

        this.id = id;

        pass = true;

        pM = ProgressManager.getInstance();

        font = new AndroidFont(routeF, w, false);
        this.routeI = routeI;
    }

    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.RenderFillRect(posX, posY, width, height, c, c2, radius);
        if ((pM.getIdActualWorld() < pM.getWorldPass()) || ( pM.getIdActualWorld() == pM.getWorldPass() && id <= pM.getLevelPass()))
        {
            //pass = true;
            graphics.CreateFont(font.getRoute(), font.getSize(), font.getBold());
            graphics.RenderText(x + w / 3,  y + h / 3, w / 3, String.valueOf(id), c2);
        }else
        {
            pass = false;
            graphics.RenderImage(graphics.createImage(routeI),x + w/4, y+ h/6, w/2, h/2);
        }
    }
    @Override
    protected boolean HandleClick() {
        if(pass)
        {
            ResourcesManager.getInstance().setIdActualLevel(this.id - 1);
            Level level = ResourcesManager.getInstance().getLevel(this.id - 1);
            SceneManager.getInstance().pushSceneStack();

            ProgressManager.getInstance().DeleteProgressInLevel();

            GameScene gS = new GameScene(4, false);
            SceneManager.getInstance().SetScene(gS);

        }


        return true;
    }
}
