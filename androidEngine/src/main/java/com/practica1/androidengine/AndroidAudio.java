package com.practica1.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

/*
 * Implementación de la interfaz `IAudioSystem` y es responsable
 * de gestionar la reproducción de audio utilizando el SoundPool de Android. Proporciona métodos para cargar y reproducir sonidos,
 * detener la reproducción de sonidos y liberar recursos de audio.
 */

public class AndroidAudio {
    private AssetManager assets;
    SoundPool soundsPool;

    public AndroidAudio(AssetManager assets) {
        this.assets = assets;
        this.soundsPool = new SoundPool.Builder().setMaxStreams(10).build();
    }

    /*
     * Carga y reproduce un sonido utilizando el SoundPool de Android. Detiene cualquier sonido que esté reproduciéndose
     * previamente con el mismo identificador y establece un OnLoadCompleteListener para reproducir el sonido una vez que se
     * haya cargado completamente.
     */
    public void playAudio(AndroidSound sound) {
        AndroidSound s = null;
        int soundId = -1;
        try {
            AssetFileDescriptor assetFileDescriptor = this.assets.openFd(sound.getSoundName());
            soundId = this.soundsPool.load(assetFileDescriptor, -1);
        } catch (Exception e) {
            throw new RuntimeException("Couln't load sound.");
        }
        if (soundId != -1)
            s = new AndroidSound(soundId, sound.getSoundName(), sound.loopSound());

        if (s.getStreamId() != -1) {
            this.soundsPool.stop(s.getStreamId());
        }

        soundsPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
    }

    /*
     * Detiene la reproducción del sonido recibido.
     */
    public void stopAudio(AndroidSound sound) {

        if (sound.getStreamId() != -1)
            this.soundsPool.stop(sound.getStreamId());
    }

    public void releaseAudio() {
        soundsPool.release();
        soundsPool = new SoundPool.Builder().setMaxStreams(10).build();
    }
}
