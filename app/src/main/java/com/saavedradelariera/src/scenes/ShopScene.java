package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.ColorJ;
import com.saavedradelariera.src.Buttons.ChangeSceneButtonBack;
import com.saavedradelariera.src.Buttons.ChangeShopPageButton;
import com.saavedradelariera.src.Buttons.ImageButton;
import com.saavedradelariera.src.Buttons.SkinButton;
import com.saavedradelariera.src.ClickListener;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.Skin;
import com.saavedradelariera.src.Text;
import com.saavedradelariera.src.VisualRectangle;

public class ShopScene extends Scene {
    private int pageId;
    private int skinWidth = 150;
    private int skinHeight = 150;
    private int startX = 50;
    private int startY = 110;
    private int padding = 20;
    private Text balance;
    private AndroidGraphics androidGraphics;
    private String amountFont = "Night.ttf";
    String currentCat;
    private ColorJ c = new ColorJ(0, 0, 0);
    String toastMsg = "";

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
        new VisualRectangle(0, 0, androidGraphics.GetWidth(), androidGraphics.GetHeight(), ShopManager.getInstance().getBackgroundColor(), true);
        currentCat = ShopManager.getInstance().getCategory(pageId);
        Text t = new Text(amountFont, 200, 50, 50, 50, currentCat, new ColorJ(0, 0, 0));
        balance = new Text(520, 90, 40, 40, "" + ShopManager.getInstance().getBalance(), c);

        ImageButton coin = new ImageButton("coin.png", 520, 20, 50, 60);
        ChangeShopPageButton cw1 = new ChangeShopPageButton(this, "arrowC1.png", 450, 35, 50, 50, true);
        ChangeShopPageButton cw2 = new ChangeShopPageButton(this, "arrowC2.png", 120, 35, 50, 50, false);
        ChangeSceneButtonBack buttonBack = new ChangeSceneButtonBack("arrow.png", 50, 35, 50, 50);
        displaySkins();
        cw1.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (pageId < ShopManager.getInstance().getCategories().size()-1) {
                    clearBalanceText();
                }
            }
        });
        cw2.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                if (pageId > 0) {
                    clearBalanceText();
                }
            }
        });
    }

    public void displaySkins() {
        int pageId = ShopManager.getInstance().getCurrentPage();
        String currentCat = ShopManager.getInstance().getCategory(pageId);
        int n = ShopManager.getInstance().getSkinsByCat(currentCat).size();
        int buttonsPerRow = 3;
        int contGlobal = 0;
        int numberOfRows = Math.min(n / buttonsPerRow + 1, 4);
        Skin skin;

        ImageButton removeSkin = new ImageButton("remove_img.png", startX, startY, skinWidth, skinHeight);

        removeSkin.setClickListener(new ClickListener() {
            @Override
            public void onClick() {
                ShopManager.getInstance().removeActiveSkin(currentCat);
                loadPage();
                showActiveIndicator(startX,startY);
            }
        });

        //display first row
        for (int col = 1; col < buttonsPerRow; col++) {
            int x = startX + col * (skinWidth + padding);
            if (contGlobal < n) {
                skin = ShopManager.getInstance().getSkinsByCat(currentCat).get(contGlobal);
                showSkin(androidGraphics, skin, x, startY);
                contGlobal++;
            } else {
                break;
            }
        }

        int row = 1;
        while (contGlobal < n && row < numberOfRows) {
            for (int col = 0; col < buttonsPerRow; col++) {
                int x = startX + col * (skinWidth + padding);
                int y = startY + row * (skinHeight + 65 + padding * 2);
                if (contGlobal < n) {
                    skin = ShopManager.getInstance().getSkinsByCat(currentCat).get(contGlobal);
                    showSkin(androidGraphics, skin, x, y);
                    contGlobal++;
                } else {
                    break;
                }
            }
            row++;
        }
    }

    private void showSkin(AndroidGraphics graphics, Skin skin, int x, int y) {
        SkinButton skinButton = new SkinButton(skin, x, y, skinWidth, skinHeight, c, amountFont, skin.getSamplePath(), skin.getPrice());

        if (skin.getBought()) { //bought
            clearPrice(x, y);
            if (ShopManager.getInstance().getActiveSkin(currentCat) == skin) {
                showActiveIndicator(x,y);
            }
        } else { //not bought
            new ImageButton("coin.png", x - 15, y + 170, 50, 60);
            new Text(amountFont, x + 55, y + 190, 60, 60, "" + skin.getPrice(), c);
        }

        skinButton.setClickListener(new ClickListener() {
            @Override
            public void onClick() {

                if (!skin.getBought()) {
                    if (ShopManager.getInstance().getBalance() >= skin.getPrice()) {
                        // Comprar skin
                        ShopManager.getInstance().addBoughtSkin(skin.getTitle());
                        skin.setBought(true);
                        balance = new Text(520, 90, 40, 40, "" + ShopManager.getInstance().getBalance(), new ColorJ(255, 255, 255));
                        ShopManager.getInstance().addBalance(-skin.getPrice());
                        balance = new Text(520, 90, 40, 40, "" + ShopManager.getInstance().getBalance(), c);
                        clearPrice(x, y);
                        ShopManager.getInstance().setActiveSkin(skin.getCategory(), skin);
                    }
                } else {
                    // Equipar Skin
                    ShopManager.getInstance().setActiveSkin(skin.getCategory(), skin);
                    loadPage();
                }
            }
        });
    }

    private void clearBalanceText() {
        new Text(520, 90, 40, 40, "" + ShopManager.getInstance().getBalance(), ShopManager.getInstance().getBackgroundColor());
    }

    private void clearPrice(int x, int y) {
        new VisualRectangle(x - 15, y + 170, 155, 80, ShopManager.getInstance().getBackgroundColor(), true);
    }

    private void showActiveIndicator(int x, int y) {
        new VisualRectangle(x,y,skinWidth,skinHeight,new ColorJ("#2be443"),false);
    }
}
