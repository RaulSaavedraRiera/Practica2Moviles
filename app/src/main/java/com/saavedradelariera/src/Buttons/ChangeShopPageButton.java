package com.saavedradelariera.src.Buttons;

import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.scenes.ShopScene;

public class ChangeShopPageButton extends ImageButton {

    boolean nextPage;
    ShopScene shopScene;

    public ChangeShopPageButton(ShopScene shopScene, String route, int x, int y, int w, int h, boolean nextPage) {
        super(route, x, y, w, h);
        this.shopScene = shopScene;
        this.nextPage = nextPage;
    }

    @Override
    protected boolean HandleClick() {
        if (ShopManager.getInstance().changePage(this.nextPage)) {
            if (this.nextPage) {
                SceneManager.getInstance().pushSceneStack();
                ShopScene sS = new ShopScene();
                SceneManager.getInstance().SetScene(sS);
            } else {
                SceneManager.getInstance().SetScene(SceneManager.getInstance().useSceneStack());
            }
        }
        return true;
    }
}
