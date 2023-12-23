package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.messages.Message;

/*interfaz de GameObject*/
public interface IGameObject {

    //permite darle un offset, en una linea pos = lineaPos + botonPos
    void render(AndroidGraphics iGraphics);

    void update(AndroidEngine IEngine, float deltaTime);

    boolean handleInput(TouchEvent e);


    void setX(int x);
    void setY(int y);
    int getX();
    int getY();
    int getWidth();
    int getHeight();

     void receiveMessage(Message m);


    boolean isOver(float x, float y);
}
