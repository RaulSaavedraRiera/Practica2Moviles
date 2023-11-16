package com.practica1.androidengine;
import com.practica1.sound.IAudioSystem;

import java.util.ArrayList;

public interface IScene {
    String name = "";

    void SetScene(AndroidGraphics graphics, IAudioSystem audioSystem);

    String GetName();

    void SetName(String n);

    void RenderScene(AndroidGraphics iGraphics);

    void UpdateScene(AndroidEngine IEngine, float deltaTime);

    void HandleInput(ArrayList<TouchEvent> events);

}
