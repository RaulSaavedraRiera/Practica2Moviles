package com.saavedradelariera.src.messages;

/*Clase para lanzar un mensaje de input, guarda el n del boton pulsado*/
public class InputColorMessage extends Message {
    public int nButton;

    public InputColorMessage(int n){
        nButton = n;
        this.id = "InputColor";
    }
}
