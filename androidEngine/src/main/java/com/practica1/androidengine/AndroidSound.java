package com.practica1.androidengine;



/**
 * Extension of the `SoundJ` class and represents a sound
 * resource in an Android application. It provides information about the sound's ID, name,
 * and loop status.
 */
public class AndroidSound {

    protected String name;
    protected boolean loop;
    int id;
    int streamId = -1;

    AndroidSound(int id, String name, boolean loop) {
        this.id = id;
        this.name = name;
        this.loop = loop;
    }

    public int getStreamId() {
        return this.streamId;
    }


    public String GetSoundName() {
        return name;
    }


    public boolean LoopSound() {
        return loop;
    }
}
