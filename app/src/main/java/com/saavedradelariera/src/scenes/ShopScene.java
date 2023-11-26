package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeShopPageButton;
import com.saavedradelariera.src.Buttons.ImageButton;
import com.saavedradelariera.src.Buttons.SkinButton;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Skin;
import com.saavedradelariera.src.Text;

public class ShopScene extends Scene {
    private int pageId;
    private int skinWidth = 150;
    private int skinHeight = 150;
    private int startX = 50;
    private int startY = 110;
    private int padding = 20;
    private AndroidGraphics androidGraphics;
    private ColorJ c = new ColorJ(0, 0, 0);

    public ShopScene() {
        pageId = ShopManager.getInstance().getCurrentPage();
    }

    @Override
    public void SetScene(AndroidGraphics graphics, AndroidAudio androidAudio) {
        super.SetScene(graphics, androidAudio);
        this.androidGraphics = graphics;

        name = "CustomizeScene";
        loadPage();
    }

    public void loadPage() {
        String currentCat = ShopManager.getInstance().getCategory(pageId);
        Text t = new Text("Night.ttf", 200, 50, 50, 50, currentCat, new ColorJ(0, 0, 0));

        ImageButton coin = new ImageButton("coin.png", 520, 30, 50, 60);
        ChangeShopPageButton cw1 = new ChangeShopPageButton(this, "arrowC1.png", 450, 35, 50, 50, true);
        ChangeShopPageButton cw2 = new ChangeShopPageButton(this, "arrowC2.png", 120, 35, 50, 50, false);
        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png", 50, 35, 50, 50);
        displaySkins();
    }

    public void displaySkins() {
        int pageId = ShopManager.getInstance().getCurrentPage();
        String currentCat = ShopManager.getInstance().getCategory(pageId);
        int n = ShopManager.getInstance().getSkinsByCat(currentCat).size();
        int buttonsPerRow = 3;
        int contGlobal = 0;
        int numberOfRows = Math.min(n / buttonsPerRow + 1, 4);

        int row = 0;
        while (contGlobal < n && row < numberOfRows) {
            for (int col = 0; col < buttonsPerRow; col++) {
                int x = startX + col * (skinWidth + padding);
                int y = startY + row * (skinHeight + padding * 2);
                if (contGlobal < n) {
                    Skin skin = ShopManager.getInstance().getSkinsByCat(currentCat).get(contGlobal);
                    showSkin(androidGraphics, skin, contGlobal, x, y);
                    contGlobal++;
                } else {
                    break;
                }
            }
            row++;
        }
    }

    private void showSkin(AndroidGraphics graphics, Skin skin, int index, int x, int y) {
        ImageButton coin = new ImageButton("coin.png", x - 15, y + 170, 50, 60);
        SkinButton skinButton = new SkinButton(x, y, skinWidth, skinHeight, c, index + 1, "Night.ttf", skin.getSamplePath(), skin.getPrice());
    }
}
