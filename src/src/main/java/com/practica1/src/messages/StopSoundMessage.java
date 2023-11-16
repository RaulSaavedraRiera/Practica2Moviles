package com.practica1.src.messages;

import com.practica1.sound.SoundJ;

/*Clase para lanzar un mensaje de parar de un sonido, guarda datos del sonido a detener*/
public class StopSoundMessage extends Message {
    public SoundJ sound;

    public StopSoundMessage(String name, boolean loop){

        this.sound = new SoundJ(name, loop);
        this.id = "StopSound";
    }
}
