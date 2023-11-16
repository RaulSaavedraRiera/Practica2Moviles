package com.practica1.desktopengine;

import com.practica1.sound.IAudioSystem;
import com.practica1.sound.ISound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;

/**
 * Clase encarga para la creacion de audio en Desktop
 */
public class DesktopAudio implements IAudioSystem {

    // Pool para la creacion de los sonidos
    private PoolSoundsDesktop pool;
    private ArrayList<DesktopSound> sounds;

    private Map<String, Clip> soundMap = new HashMap<>();

    DesktopAudio()
    {
        pool = new PoolSoundsDesktop(10);
        sounds = new ArrayList<DesktopSound>();
    }

    @Override
    public void PlayAudio(ISound sound) {
        try{
            DesktopSound c = pool.newEvent(sound.GetSoundName());
            sounds.add(c);

            c.setActualClip(c.createClip(sound.GetSoundName()));
            c.getActualClip().setFramePosition(0);
            c.getActualClip().start();
            if(!soundMap.containsKey(sound.GetSoundName())){
                soundMap.put(sound.GetSoundName(), c.getActualClip());
            }

        }catch (Exception e){}

        freeEvents(sounds);
    }

    //Parar un audio que se este ejecutando
    @Override
    public void StopAudio(ISound sound) {
        DesktopSound d = (DesktopSound)sound;
        d.getActualClip().stop();
    }

    @Override
    public void ReleaseAudio() {
      
        for (Map.Entry<String, Clip> entry : soundMap.entrySet()) {
            entry.getValue().close();

        }
        soundMap.clear();
    }

    //Liberamos los eventos de la pool
    private void freeEvents(ArrayList<DesktopSound> event) {
        for (DesktopSound e : event) {
            // Comprobamos si el audio ha acabado liberarmos su espacio en la pool
            if(!e.getActualClip().isRunning()){
                pool.freeEvent(e);
            }
        }
        event.clear();
    }

}
