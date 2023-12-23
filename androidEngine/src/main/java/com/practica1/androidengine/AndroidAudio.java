package com.practica1.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

/**
 * Implementation of the `IAudioSystem` interface and is responsible
 * for managing audio playback using Android's SoundPool. It provides methods to load and play audio
 * sounds, stop playing sounds, and release audio resources.
 */
public class AndroidAudio {
    private AssetManager assets;
    SoundPool soundsPool;

    public AndroidAudio(AssetManager assets) {
        this.assets = assets;
        this.soundsPool = new SoundPool.Builder().setMaxStreams(10).build();
    }

    /**
     * Loads and plays an audio sound using Android's SoundPool. It stops any previously playing
     * sound with the same identifier and sets an OnLoadCompleteListener to play the sound once it's
     * fully loaded.
     * @param sound Sound to play
     */

    public void playAudio(AndroidSound sound) {
        AndroidSound s = null;
        int soundId = -1;
        try {
            AssetFileDescriptor assetFileDescriptor = this.assets.openFd(sound.GetSoundName());
            soundId = this.soundsPool.load(assetFileDescriptor, -1);
        } catch (Exception e) {
            throw new RuntimeException("Couln't load sound.");
        }
        if (soundId != -1)
            s = new AndroidSound(soundId, sound.GetSoundName(), sound.LoopSound());

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

    /**
     * Stops playing the received sound.
     * @param sound Sound to stop playing.
     */

    public void stopAudio(AndroidSound sound) {

        if(sound.getStreamId() != -1)
            this.soundsPool.stop(sound.getStreamId());
    }

    public void releaseAudio() {
        soundsPool.release();
        soundsPool = new SoundPool.Builder().setMaxStreams(10).build();
    }
}
