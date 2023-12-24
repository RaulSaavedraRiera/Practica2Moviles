package com.practica1.androidengine;

/*
 * Extensión de la clase `SoundJ` y representa un recurso de sonido
 * en una aplicación de Android. Proporciona información sobre el ID, nombre y
 * estado de bucle del sonido.
 */
public class AndroidSound {

    protected String name;
    protected boolean loop;
    int id;
    int streamId = -1;

    public AndroidSound(int id, String name, boolean loop) {
        this.id = id;
        this.name = name;
        this.loop = loop;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public String getSoundName() {
        return name;
    }

    public boolean loopSound() {
        return loop;
    }
}
