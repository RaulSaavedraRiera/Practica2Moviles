package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ImageButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.GameManager;
import com.saavedradelariera.src.GameObject;
import com.saavedradelariera.src.ImageBackground;
import com.saavedradelariera.src.InputSolution;
import com.saavedradelariera.src.Level;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.VisualRectangle;
import com.saavedradelariera.src.messages.DaltonicChangeSolicitateMessage;

import java.util.ArrayList;

/*Escena de juego principal*/
public class GameScene extends Scene {
    int nButtons, nRows, nColors, difficult;
    boolean isQuickGame, loadState;
    GameManager gameManager;
    ArrayList<AndroidImage> iconImages;
    AndroidImage backgroundImage;
    ColorJ primaryColor;
    ResourcesManager resourcesManager;
    ShopManager shopManager;

    public GameScene(int diff, boolean isQuickGame, boolean loadState) {

        inverseRender = true;

        difficult = diff;
        this.isQuickGame = isQuickGame;
        this.loadState = loadState;

        resourcesManager = ResourcesManager.getInstance();
        shopManager = ShopManager.getInstance();
    }

    /*
    Configura la escena de juego, establece el fondo y elementos visuales según la dificultad y tipo
    de juego. Crea un objeto GameManager, inicializa elementos como texto y botones, y gestiona la
    interfaz de entrada de soluciones. Finalmente, prepara la escena para el juego mediante la
    inicialización del GameManager.
     */
    @Override
    public void setScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.setScene(graphics, audioSystem);

        GameObject background = null;

        if (isQuickGame) {
            primaryColor = shopManager.getBackgroundColor();
            iconImages = resourcesManager.loadGameIcons(graphics);
            backgroundImage = resourcesManager.getBackground(graphics, true);
            if (backgroundImage == null)
                background = new ColorBackground(shopManager.getBackgroundColor());

        } else {
            backgroundImage = resourcesManager.getBackground(graphics, false);

            int i = resourcesManager.getSkinsId();
            if(i != -1)
                iconImages = resourcesManager.loadLevelIcons(i, graphics);

            primaryColor = new ColorJ("#ffffff");
        }

        if (backgroundImage != null) {
            background = new ImageBackground(backgroundImage);
        }

        setSceneSettings(difficult);
        name = "GameScene";

        //le pasamos al manager las columnas para que las gestione
        gameManager = new GameManager();

        if (iconImages != null)
            gameManager.setIconImages(iconImages);

        //parte superior del nivel
        new Text("Night.ttf", 200, 50, 25, 50, "Averigua el código", new ColorJ(0, 0, 0));
        new ChangeSceneButtonBack("X.png", 70, 50, 30, 30);
        //si no hay imagenes metemos el daltonic button
        if (iconImages == null) {
            ImageButton daltonic = new ImageButton("ojo.png", 500, 40, 50, 50);
            daltonic.setClickListener(new ClickListener() {
                @Override
                public void onClick() {
                    SceneManager.getInstance().sendMessageToActiveScene(new DaltonicChangeSolicitateMessage());
                }
            });
        }

        //creamos el input solution
        if (iconImages == null)
            new InputSolution(0, (int) (graphics.getHeightRelative() * 0.9f), graphics.getWidthRelative(), (int) (graphics.getHeightRelative() * 0.1f), nColors, primaryColor, gameManager.getColors(), false);
            //o con sprites
        else
            new InputSolution(0, (int) (graphics.getHeightRelative() * 0.9f), graphics.getWidthRelative(), (int) (graphics.getHeightRelative() * 0.1f), nColors, primaryColor, iconImages);

        //rectangulo para tapar las partes de arriba y de abajo
        new VisualRectangle(0, (int) (-graphics.getHeightRelative() * 0.4f), (int) graphics.getWidthRelative(), (int) (graphics.getHeightRelative() * 0.5f), primaryColor, true);
        new VisualRectangle(0, (int) (graphics.getHeightRelative()), (int) graphics.getWidthRelative(), (int) (graphics.getHeightRelative() * 0.5f), primaryColor, true);

        gameManager.init(background, difficult, nButtons, nRows, graphics, loadState);


    }

    /*
    Devuelve una cadena que representa el estado de la escena, incluido el nivel actual del juego.
     */
    @Override
    public String getStateScene() {
        String level;
        if (isQuickGame)
            level = "9999";
        else {
            int world = resourcesManager.getIdActualWorld();
            if (world < 10)
                level = "0" + world;
            else
                level = Integer.toString(world);

            int currentL = resourcesManager.getIdActualLevel();
            if (currentL < 10)
                level += "0" + currentL;
            else
                level += Integer.toString(currentL);
        }

        return level + gameManager.getLevelState();
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    /*
    Configura los atributos de la escena (nButtons, nRows, nColors) en función de la dificultad del juego.
     */
    void setSceneSettings(int difficult) {
        switch (difficult) {
            case 0:
                nButtons = 4;
                nRows = 6;
                nColors = 4;
                break;
            case 1:
                nButtons = 4;
                nRows = 8;
                nColors = 6;
                break;
            case 2:
                nButtons = 5;
                nRows = 10;
                nColors = 8;
                break;
            case 3:
                nButtons = 6;
                nRows = 10;
                nColors = 9;
                break;
            //Nivel personalizado

            case 4:
                Level aux = resourcesManager.getActualLevel();
                nButtons = aux.getCodeSize();
                nRows = aux.getAttempts();
                nColors = aux.getCodeOpt();
                break;
        }
    }
}