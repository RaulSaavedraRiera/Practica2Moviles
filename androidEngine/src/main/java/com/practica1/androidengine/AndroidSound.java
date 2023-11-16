package com.practica1.androidengine;

import com.practica1.sound.SoundJ;

/**
 * Extension of the `SoundJ` class and represents a sound
 * resource in an Android application. It provides information about the sound's ID, name,
 * and loop status.
 */
public class AndroidSound extends SoundJ {
    int id;
    int streamId = -1;

    AndroidSound(int id, String name, boolean loop) {
        super(name, loop);
        this.id = id;
        this.name = name;
    }

    public int getStreamId() {
        return this.streamId;
    }

    @Override
    public String GetSoundName() {
        return name;
    }

    @Override
    public boolean LoopSound() {
        return loop;
    }
}
