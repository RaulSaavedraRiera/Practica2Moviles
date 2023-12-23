package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidAudio;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.messages.PlaySoundMessage;
import com.saavedradelariera.src.messages.StopSoundMessage;

/*Clase del juego para gestionar el audio*/
public class AudioManager extends GameObject {
    AndroidAudio audioSystem;

    //obtiene el audio del motor
    public AudioManager(AndroidAudio audioSystem) {
        super(0, 0, 0, 0);
        //solicitamos AudioSystem al engine
        this.audioSystem = audioSystem;
    }

    //recibe los mensajes y reproduce o detiene audios con llamadas al motor
    @Override
    public void receiveMessage(Message m) {
        switch (m.id) {
            case "PlaySound":
                audioSystem.PlayAudio(((PlaySoundMessage) m).sound);
                break;
            case "StopSound":
                audioSystem.StopAudio(((StopSoundMessage) m).sound);
                break;
            case "ReleaseSound":
                audioSystem.ReleaseAudio();
                break;
        }
    }
}
