package com.saavedradelariera.src.Buttons;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.messages.DaltonicChangeSolicitateMessage;

/*Botón de daltonismo; cuenta con una imagen fija y si es pulsado manda un mensaje de cambio de dicho modo*/
public class DaltonicButton extends ImageButton {

    public DaltonicButton(String route, int x, int y, int w, int h ){
        super(route, x,y,w,h);
    }


    @Override
    protected boolean HandleClick() {
        SceneManager.getInstance().SendMessageToActiveScene(new DaltonicChangeSolicitateMessage());
        return true;
    }
}
