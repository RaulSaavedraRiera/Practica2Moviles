package com.saavedradelariera.src.messages;


/*Clase para lanzar un mensaje de parar de un sonido, guarda datos del sonido a detener*/
public class ReleaseSoundMessage extends Message {

    public ReleaseSoundMessage(){
        this.id = "ReleaseSound";
    }
}
