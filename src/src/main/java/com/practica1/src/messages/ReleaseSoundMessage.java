package com.practica1.src.messages;

import com.practica1.sound.SoundJ;

/*Clase para lanzar un mensaje de parar de un sonido, guarda datos del sonido a detener*/
public class ReleaseSoundMessage extends Message {

    public ReleaseSoundMessage(){
        this.id = "ReleaseSound";
    }
}
