package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.TouchEvent;

/*interfaz de GameObject*/
public interface IGameObject {

    //permite darle un offset, en una linea pos = lineaPos + botonPos
    void Render(AndroidGraphics iGraphics);

    void Update(AndroidEngine IEngine, float deltaTime);

    boolean HandleInput(TouchEvent e);


    void SetX(int x);
    void SetY(int y);
    int GetX();
    int GetY();
    int GetWidth();
    int GetHeight();

    boolean ISOver(float x, float y);
}
