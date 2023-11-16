package com.practica1.sound;

public class SoundJ implements ISound{

    protected String name;
    protected boolean loop;

    public SoundJ(String name, boolean loop){
        this.name = name;
        this.loop = loop;
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
