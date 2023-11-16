package com.practica1.input;

import com.practica1.pool.IPool;

/**
 * Clase que almacena la informacion sobre los eventos de input. Esta implementa
 * IPool para poder saber el estado de este en la pool que se crean en los InputHandlers
 */
public class TouchEvent implements IPool {
    private TouchEventType type;
    private float x;
    private float y;


    public TouchEvent() {
    }

    public TouchEventType getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setType(TouchEventType type) {
        this.type = type;
    }

    @Override
    public void free() {
        //Marcamos el evento como libre al iniciarlo con los valores minimos
        x = Integer.MIN_VALUE;
        y = Integer.MIN_VALUE;
    }

    @Override
    public boolean avaible() {
        //Sabemos que esta libre si su valor es minimo
        return x == Integer.MIN_VALUE;
    }

    @Override
    public void allocate() {
        //Marcamos como ocupado el evento al ponerlo con valores máximos
        x = Integer.MAX_VALUE;
    }

    public enum TouchEventType {

        TOUCH_DOWN,
        TOUCH_UP,
        CLICK,
        NONE;
    }

}