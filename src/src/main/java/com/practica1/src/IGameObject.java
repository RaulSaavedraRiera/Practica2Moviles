package com.practica1.src;

import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;

/*interfaz de GameObject*/
public interface IGameObject {

    //permite darle un offset, en una linea pos = lineaPos + botonPos
    void Render(IGraphics iGraphics);

    void Update(IEngine IEngine, float deltaTime);

    boolean HandleInput(TouchEvent e);


    void SetX(int x);
    void SetY(int y);
    int GetX();
    int GetY();
    int GetWidth();
    int GetHeight();

    boolean ISOver(float x, float y);
}
