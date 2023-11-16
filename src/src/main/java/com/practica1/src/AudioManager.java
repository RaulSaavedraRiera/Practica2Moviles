package com.practica1.src;


import com.practica1.src.messages.InputColorMessage;
import com.practica1.src.messages.Message;
import com.practica1.src.messages.PlaySoundMessage;
import com.practica1.src.messages.StopSoundMessage;

/*Clase del juego para gestionar el audio*/
public class AudioManager extends GameObject{
    IAudioSystem audioSystem;

    //obtiene el audio del motor
    public AudioManager(IAudioSystem audioSystem) {
        super(0,0,0,0);
        //solicitamos AudioSystem al engine
        this.audioSystem = audioSystem;
    }


    //recibe los mensajes y reproduce o detiene audios con llamadas al motor
    @Override
    public void ReceiveMessage(Message m) {
        switch(m.id)
        {
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
