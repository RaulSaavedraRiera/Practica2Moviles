package com.practica1.engine;

import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;
import com.practica1.sound.IAudioSystem;

import java.util.ArrayList;

public interface IScene {
    String name = "";

    void SetScene(IGraphics graphics, IAudioSystem audioSystem);

    String GetName();

    void SetName(String n);

    void RenderScene(IGraphics iGraphics);

    void UpdateScene(IEngine IEngine, float deltaTime);

    void HandleInput(ArrayList<TouchEvent> events);

}
