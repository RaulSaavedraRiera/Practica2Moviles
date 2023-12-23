package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.messages.Message;

/*Clase abstracta gameObject sobre la que creamos la mayoría de entidades, implementa los métodos
 * comunes a todos*/
public abstract class GameObject implements IGameObject {

    protected int posX, posY, width, height;

    public GameObject(int x, int y, int w, int h) {
        posX = x;
        posY = y;
        width = w;
        height = h;

        SceneManager.getInstance().addGameObjectToActiveScene(this);
    }

    @Override
    public void render(AndroidGraphics iGraphics) {

    }

    @Override
    public void update(AndroidEngine IEngine, float deltaTime) {
    }

    @Override
    public void receiveMessage(Message m) {
    }

    //TODO se queda con input
    @Override
    public boolean handleInput(TouchEvent e) {
        return false;
    }

    @Override
    public void setX(int x) {
        posX = x;
    }

    public void setY(int y) {
        posY = y;
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isOver(float x, float y) {
        if (x >= getX() && x <= getX() + getWidth() &&
                y >= getY() && y <= getY() + getHeight())
            return true;
        else
            return false;
    }
}
