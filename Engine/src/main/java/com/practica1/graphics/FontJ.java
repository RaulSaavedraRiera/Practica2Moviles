package com.practica1.graphics;

/**
 * Clase encargada de guardar los datos correspondientes para la inicializacion y creacion de las fuentes
 */
public class FontJ implements IFont {
    protected String Route;
    protected int Size;
    protected boolean Bold;

    public FontJ(String route, int size, boolean bold){
        Route = route;
        Bold = bold;
        Size = size;
    }

    @Override
    public String getName() {
        return Route;
    }

    @Override
    public boolean getBold() {
        return Bold;
    }

    @Override
    public int getSize() {
        return Size;
    }

    @Override
    public void setSize(int size) {
    }

    @Override
    public void setBold(boolean to) {
    }
}
