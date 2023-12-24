package com.practica1.androidengine;

import java.util.ArrayList;

/*
Interfaz de escena de la que heredarán las clases Scene y dervidadas d ela aplicación que construyamos
sobre el motor
*/

public interface IScene {
    String name = "";

    void setScene(AndroidGraphics graphics, AndroidAudio audioSystem);

    String getName();

    void setName(String n);

    void renderScene(AndroidGraphics iGraphics);

    void updateScene(AndroidEngine IEngine, float deltaTime);

    void handleInput(ArrayList<TouchEvent> events);

}
