package com.practica1.src;

import com.practica1.engine.IEngine;
import com.practica1.src.messages.Message;
import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;

/*Clase abstracta gameObject sobre la que creamos la mayoría de entidades, implementa los métodos
* comunes a todos*/
public abstract class GameObject implements IGameObject {

    protected int posX, posY, width, height;

    public GameObject(int x, int y, int w, int h){
        posX = x;
        posY = y;
        width = w;
        height = h;

        SceneManager.getInstance().AddGameObjectToActiveScene(this);
    }

    @Override
    public void Render(IGraphics iGraphics) {

    }

    @Override
    public void Update(IEngine IEngine, float deltaTime) {

    }

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
