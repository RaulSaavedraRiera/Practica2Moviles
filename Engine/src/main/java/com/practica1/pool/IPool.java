package com.practica1.pool;

/**
 * Interfaz para los eventos que estarán dentro de la pool
 */
public interface IPool {

    void free();

    boolean avaible();

    void allocate();
}
