package com.saavedradelariera.src.messages;

import com.practica1.androidengine.AndroidSound;

/*Clase para lanzar un mensaje de reproducir un sonido, guarda datos del sonido a detener*/
public class PlaySoundMessage extends Message {

    public AndroidSound sound;

    public PlaySoundMessage(String name, boolean loop){

        this.sound = new AndroidSound(0, name, loop);
        this.id = "PlaySound";
    }
}
