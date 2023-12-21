package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.messages.Message;

/*Clase abstracta gameObject sobre la que creamos la mayoría de entidades, implementa los métodos
* comunes a todos*/
public abstract class GameObject implements IGameObject {

    protected int posX, posY, width, height;

    public GameObject(int x, int y, int w, int h){
        posX = x;
        posY = y;
        width = w;
        height = h;

        SceneManager.getInstance().addGameObjectToActiveScene(this);
    }

    @Override
    public void Render(AndroidGraphics iGraphics) {

    }

    @Override
    public void Update(AndroidEngine IEngine, float deltaTime) {

    }

    @Override
    public void ReceiveMessage(Message m) {
    }

    //TODO se queda con input
    @Override
    public boolean HandleInput(TouchEvent e) {
            return false;
    }

    @Override
    public void SetX(int x) {posX = x;}

    public void SetY(int y) {posY = y;}

    @Override
    public int GetX() {
        return posX;
    }

    @Override
    public int GetY() {
        return posY;
    }

    @Override
    public int GetWidth() {
        return width;
    }

    @Override
    public int GetHeight() {
        return height;
    }

    @Override
    public boolean ISOver(float x, float y) {
        if (x >= GetX() && x <= GetX() + GetWidth() &&
               y >= GetY() && y <= GetY() + GetHeight())
            return true;
        else
            return false;
    }
}
