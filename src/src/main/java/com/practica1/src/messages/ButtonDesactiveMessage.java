package com.practica1.src.messages;

/*Clase para lanzar un mensaje de desactivar un boton, guarda el n de botón pulsado*/
public class ButtonDesactiveMessage extends Message {

    public int nB;

    public ButtonDesactiveMessage(int numberButtonInRow){
        this.nB = numberButtonInRow;
        this.id = "ButtonDesactive";
    }
}
