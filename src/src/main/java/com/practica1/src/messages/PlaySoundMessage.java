package com.practica1.src.messages;

import com.practica1.sound.SoundJ;

/*Clase para lanzar un mensaje de reproducir un sonido, guarda datos del sonido a detener*/
public class PlaySoundMessage extends Message {

    public SoundJ sound;

    public PlaySoundMessage(String name, boolean loop){

        this.sound = new SoundJ(name, loop);
        this.id = "PlaySound";
    }
}
