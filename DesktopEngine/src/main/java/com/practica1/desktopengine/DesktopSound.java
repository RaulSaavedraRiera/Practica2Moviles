package com.practica1.desktopengine;
import com.practica1.pool.IPool;
import com.practica1.sound.SoundJ;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase encarga para la creacion de audio en Desktop
 * Estos DesktopSounds se encontraran dentro de una pool para imitar el funcioneamiento de
 * android
 */
public class DesktopSound extends SoundJ implements IPool {
    private Clip actualClip;
    private int use;

    public DesktopSound(String name, boolean loop) {
        super(name, loop);
    }

    @Override
    public String GetSoundName() {
        return name;
    }

    @Override
    public boolean LoopSound() {
        return loop;
    }

    public Clip createClip(String name)
    {
        //Intentamos crearlo
        try {
            File audioFile = new File("assets/" + name);
            AudioInputStream audioStream =
                    AudioSystem.getAudioInputStream(audioFile);

            actualClip = AudioSystem.getClip();
            actualClip.open(audioStream);


        } catch (Exception e) {
            System.err.println("audio no encontrado");
            e.printStackTrace();
        }

        if (loop)
            actualClip.loop(Clip.LOOP_CONTINUOUSLY);

        return actualClip;
    }

    @Override
    public void free() {
        //Marcamos el evento como libre al iniciarlo con los valores minimos
        use = Integer.MIN_VALUE;
    }

    @Override
    public boolean avaible() {
        return use == Integer.MIN_VALUE;
    }

    @Override
    public void allocate() {
        use = Integer.MAX_VALUE;
    }

    public Clip getActualClip() {
        return actualClip;
    }

    public void setActualClip(Clip actualClip) {
        this.actualClip = actualClip;
    }
}
