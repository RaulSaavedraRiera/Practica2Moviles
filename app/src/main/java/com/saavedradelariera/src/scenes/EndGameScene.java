package com.saavedradelariera.src.scenes;

import android.graphics.Bitmap;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.mobileManagers.AdsFinishCallback;
import com.practica1.androidengine.mobileManagers.ScreenShootFinish;
import com.saavedradelariera.src.ButtonArray;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
import com.saavedradelariera.src.GameManager;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Text;

import java.util.ArrayList;


public class EndGameScene extends Scene {
    boolean win, daltonic, typeGame;
    int tries, gameDifficult;
    ArrayList<ColorJ> colors;
    ArrayList<Integer> numbers;
    ArrayList<AndroidImage> iconImages;
    public EndGameScene(boolean win, int tries, ArrayList<ColorJ> colors, ArrayList<Integer> numbers, boolean daltonic, int gameDifficult, boolean typeGame) {
        this.win = win;
        this.tries = tries;
        this.colors = colors;
        this.numbers = numbers;
        this.daltonic = daltonic;
        this.gameDifficult = gameDifficult;
        this.typeGame = typeGame;

    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.SetScene(graphics, audioSystem);

        new ColorBackground(ShopManager.getInstance().getBackgroundColor());
        ColorJ buttonsColor = ShopManager.getInstance().getButtonsColor();
        ColorJ blackColor = new ColorJ(0, 0, 0);


        if(typeGame)
            iconImages = ResourcesManager.getInstance().LoadGameIcons(graphics);
        else
            iconImages = ResourcesManager.getInstance().LoadLevelIcons(ResourcesManager.getInstance().getSkinsId(), graphics);

        new Text("Night.ttf", 270, 300, 20, 50, "código:", blackColor);
        ButtonArray b = new ButtonArray(100, 350, 400, 100);

        if(iconImages == null)
            b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, colors, false, false, daltonic);
        else
            b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, iconImages, false, false);

            //Si se gana la partida
        if (win) {
            new Text("Night.ttf", 150, 120, 45, 125, "ENHORABUENA!!", blackColor);
            new Text("Night.ttf", 175, 175, 18, 50, "Has averiguado el código en:", blackColor);
            new Text("Night.ttf", 230, 250, 30, 70, String.valueOf(tries + 1) + " intentos", blackColor);

            GenericButton shareB = new GenericButton(100, 510, 400, 100, buttonsColor, blackColor, 10);
            new Text("Night.ttf", 175, 550, 55, 55, "COMPARTIR", blackColor);
            shareB.setClickListener(new ClickListener() {
                @Override
                public void onClick() {
                    SceneManager.getInstance().getEngine().GetGraphics().generateScreenShoot(0
                            , 0, SceneManager.getInstance().getEngine().GetGraphics().GetWidth(), SceneManager.getInstance().getEngine().GetGraphics().GetHeight() / 2, new ScreenShootFinish() {
                                @Override
                                public void doAction(Bitmap bitmap) {
                                    SceneManager.getInstance().getEngine().SolicitateShare(bitmap, "ME HE PASADO EL MASTERMIND");
                                }
                            });
                }
            });

            //Si es un nivel de mundo
            if(!typeGame)
            {
                ProgressManager.getInstance().setLevelPass();
                GenericButton next = new GenericButton(100, 700, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 180, 710, 36, 90, "Siguiente nivel", blackColor);
                next.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        SceneManager.getInstance().useSceneStack();
                        WorldScene wS = new WorldScene();
                        SceneManager.getInstance().SetScene(wS);
                    }
                });
            }else {

                GenericButton playAgainB = new GenericButton(100, 700, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 180, 710, 36, 90, "Volver a jugar", blackColor);
                playAgainB.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        SceneManager.getInstance().useSceneStack();
                        GameScene gS = new GameScene(gameDifficult, true, false);
                        SceneManager.getInstance().SetScene(gS);
                    }
                });

                GenericButton finalButton = new GenericButton(100, 775, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 160, 785, 36, 90, "Elegir dificultad", blackColor);
                finalButton.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        ChooseDifficultyScene mS = new ChooseDifficultyScene();
                        SceneManager.getInstance().SetScene(mS);
                    }
                });
            }
            // Si se pierde la partida
        } else {
            new Text("Night.ttf", 200, 120, 45, 125, "GAME OVER", blackColor);
            new Text("Night.ttf", 175, 175, 18, 50, "Te has quedado sin intentos", blackColor);
            GenericButton adButton = new GenericButton(100, 510, 400, 100, buttonsColor, blackColor,  10);
            new Text("Night.ttf",140, 535, 50, 50, "+ 2 intentos", blackColor);
            adButton.setClickListener(new ClickListener() {
                @Override
                public void onClick() {
                    SceneManager.getInstance().getEngine().SolicitateRewardAd(new AdsFinishCallback() {
                        @Override
                        public void doAction() {

                            GameScene gS = (GameScene) SceneManager.getInstance().getPeckStack();

                            SceneManager.getInstance().useSceneStack();
                            SceneManager.getInstance().ReturnToScene(gS);

                            gS.getGameManager().AddRows(2);
                        }
                    });
                }
            });

            GenericButton playAgainB = new GenericButton(100, 700, 400, 50, buttonsColor, blackColor, 10);
            new Text("Night.ttf", 180, 710, 36, 90, "Volver a jugar", blackColor);
            playAgainB.setClickListener(new ClickListener() {
                @Override
                public void onClick() {
                    SceneManager.getInstance().useSceneStack();
                    GameScene gS = new GameScene(gameDifficult, typeGame, false);
                    SceneManager.getInstance().SetScene(gS);
                }
            });
        }

        GenericButton finalButton = new GenericButton(100, 775, 400, 50, buttonsColor, blackColor, 10);
        new Text("Night.ttf", 260, 785, 36, 90, "Menu", blackColor);
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