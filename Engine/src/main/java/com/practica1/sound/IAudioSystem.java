package com.practica1.sound;
public interface IAudioSystem {
    void PlayAudio(ISound sound);
    void StopAudio(ISound sound);
    void ReleaseAudio();
}
