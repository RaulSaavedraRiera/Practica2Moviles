package com.practica1.androidengine;
import java.util.ArrayList;

public interface IScene {
    String name = "";

    void SetScene(AndroidGraphics graphics, AndroidAudio audioSystem);

    String GetName();

    void SetName(String n);

    void RenderScene(AndroidGraphics iGraphics);

    void UpdateScene(AndroidEngine IEngine, float deltaTime);

    void HandleInput(ArrayList<TouchEvent> events);

}
