package com.practica1.androidengine;

/*Clase para conectar el entorno de java con la función nativa (C++) de generar un hash*/
public class NDKManager {
    public static native String generateHash(String data);
}
