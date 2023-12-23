package com.saavedradelariera.src.Buttons;

import com.practica1.androidengine.AndroidFont;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
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
    public void render(AndroidGraphics graphics) {
        graphics.renderFillRect(posX, posY, width, height, c, c2, radius);
        if ((pM.getIdActualWorld() < pM.getWorldPass()) ||
                ( pM.getIdActualWorld() == pM.getWorldPass() && id <= pM.getLevelPass()))
        {
            graphics.createFont(font.getRoute(), font.getSize(), font.getBold());
            graphics.renderText(x + w / 3,  y + h / 3, w / 3, String.valueOf(id + 1), c2);
        }else
        {
            pass = false;
            graphics.renderImage(graphics.createImage(routeI),x + w/4, y+ h/6, w/2, h/2);
        }
    }
    @Override
    protected boolean HandleClick() {
        if(pass)
        {
            ResourcesManager.getInstance().setIdActualLevel(this.id);
            ResourcesManager.getInstance().getLevel(this.id , pM.getIdActualWorld());
            SceneManager.getInstance().pushSceneStack();

            ProgressManager.getInstance().DeleteProgressInLevel();

            GameScene gS = new GameScene(4, false, false);
            SceneManager.getInstance().setScene(gS);
        }
        return true;
    }
}
