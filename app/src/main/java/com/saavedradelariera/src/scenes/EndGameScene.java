package com.saavedradelariera.src.scenes;

import android.graphics.Bitmap;
import android.util.Pair;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;
import com.practica1.androidengine.mobileManagers.AdsFinishCallback;
import com.practica1.androidengine.mobileManagers.ScreenShootFinish;
import com.saavedradelariera.src.ButtonArray;
import com.saavedradelariera.src.Buttons.GenericButton;
import com.saavedradelariera.src.Buttons.ImageButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ColorBackground;
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
    ShopManager shopManager;
    ResourcesManager resourcesManager;
    ProgressManager progressManager;
    SceneManager sceneManager;

    public EndGameScene(boolean win, int tries, ArrayList<ColorJ> colors, ArrayList<Integer> numbers, boolean daltonic, int gameDifficult, boolean typeGame) {
        this.win = win;
        this.tries = tries;
        this.colors = colors;
        this.numbers = numbers;
        this.daltonic = daltonic;
        this.gameDifficult = gameDifficult;
        this.typeGame = typeGame;

        shopManager = ShopManager.getInstance();
        resourcesManager = ResourcesManager.getInstance();
        sceneManager = SceneManager.getInstance();
        progressManager = ProgressManager.getInstance();

    }

    @Override
    public void setScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        super.setScene(graphics, audioSystem);

        new ColorBackground(shopManager.getBackgroundColor());
        ColorJ buttonsColor = shopManager.getButtonsColor();
        ColorJ blackColor = new ColorJ(0, 0, 0);


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
                    sceneManager.getEngine().GetGraphics().generateScreenShoot(0
                            , 0, sceneManager.getEngine().GetGraphics().GetWidth(), sceneManager.getEngine().GetGraphics().GetHeight() / 2, new ScreenShootFinish() {
                                @Override
                                public void doAction(Bitmap bitmap) {
                                    sceneManager.getEngine().SolicitateShare(bitmap, "ME HE PASADO EL MASTERMIND");
                                }
                            });
                }
            });
            if(typeGame)
                iconImages = resourcesManager.LoadGameIcons(graphics);
            else
                iconImages = resourcesManager.LoadLevelIcons(resourcesManager.getSkinsId(), graphics);

            new Text("Night.ttf", 270, 300, 20, 50, "código:", blackColor);
            ButtonArray b = new ButtonArray(100, 350, 400, 100);

            if(iconImages == null)
                b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, colors, false, false, daltonic);
            else
                b.GenerateEnableButtons(numbers.size(), 0.9f, 1.1f, 1f, numbers, iconImages, false, false);


            shopManager.addBalance(15);
            new Text("Night.ttf", 180, 875, 40, 40, " + 15 - TOTAL " + shopManager.getBalance(), blackColor);
            new ImageButton("coin.png", 120, 850, 60, 60);


            //Si es un nivel de mundo
            if(!typeGame)
            {
                progressManager.setLevelPass();
                GenericButton next = new GenericButton(100, 700, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 180, 710, 36, 90, "Siguiente nivel", blackColor);
                next.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        sceneManager.useSceneStack();


                        Pair<Integer, Integer> nextLevelInfo = progressManager.getNextLevelInfo();

                        if(nextLevelInfo.first != -1 && nextLevelInfo.second != -1) {
                            resourcesManager.setIdActualLevel(nextLevelInfo.first);
                            resourcesManager.getLevel(nextLevelInfo.first , nextLevelInfo.second);
                            resourcesManager.setWorld(nextLevelInfo.second);
                            progressManager.DeleteProgressInLevel();
                            GameScene gS = new GameScene(4, false, false);
                            sceneManager.setScene(gS);
                        }else {
                            sceneManager.setScene(new MenuScene());
                        }

                    }
                });
            }else {

                GenericButton playAgainB = new GenericButton(100, 700, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 180, 710, 36, 90, "Volver a jugar", blackColor);
                playAgainB.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        sceneManager.useSceneStack();
                        GameScene gS;

                        if(resourcesManager.getIdActualLevel() == progressManager.getLevelPass() &&
                                resourcesManager.getIdActualWorld() == progressManager.getWorldPass())
                            gS = new GameScene(4, false, false);
                        else
                            gS = new GameScene(4, false, false);

                        sceneManager.setScene(gS);
                    }
                });

                GenericButton finalButton = new GenericButton(100, 775, 400, 50, buttonsColor, blackColor, 10);
                new Text("Night.ttf", 160, 785, 36, 90, "Elegir dificultad", blackColor);
                finalButton.setClickListener(new ClickListener() {
                    @Override
                    public void onClick() {
                        ChooseDifficultyScene mS = new ChooseDifficultyScene();
                        sceneManager.setScene(mS);
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
                    sceneManager.getEngine().SolicitateRewardAd(new AdsFinishCallback() {
                        @Override
                        public void doAction() {
                            GameScene gS = (GameScene) sceneManager.getPeckStack();
                            sceneManager.useSceneStack();
                            sceneManager.returnToScene(gS);
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
                    sceneManager.useSceneStack();
                    GameScene gS = new GameScene(gameDifficult, typeGame, false);
                    sceneManager.setScene(gS);
                }
            });
        }

        GenericButton finalButton = new GenericButton(100, 775, 400, 50, buttonsColor, blackColor, 10);
        new Text("Night.ttf", 260, 785, 36, 90, "Menu", blackColor);
        finalButton.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                sceneManager.useSceneStack();
                MenuScene mS = new MenuScene();
                sceneManager.setScene(mS);
            }
        });


    }
}