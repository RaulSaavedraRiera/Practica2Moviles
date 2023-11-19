package com.saavedradelariera.src.Buttons;

/*Botón de daltonismo; cuenta con una imagen fija y si es pulsado manda un mensaje de cambio de dicho modo*/
public class ChangeWorldButton extends ImageButton {

    public ChangeWorldButton(String route, int x, int y, int w, int h ){
        super(route, x,y,w,h);
    }

    @Override
    protected boolean HandleClick() {

        return true;
    }
}
