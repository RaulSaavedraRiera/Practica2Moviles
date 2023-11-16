package com.saavedradelariera.src.messages;

import com.practica1.androidengine.AndroidSound;

/*Clase para lanzar un mensaje de parar de un sonido, guarda datos del sonido a detener*/
public class StopSoundMessage extends Message {
    public AndroidSound sound;

    public StopSoundMessage(String name, boolean loop){

        this.sound = new AndroidSound(1, name, loop);
        this.id = "StopSound";
    }
}
