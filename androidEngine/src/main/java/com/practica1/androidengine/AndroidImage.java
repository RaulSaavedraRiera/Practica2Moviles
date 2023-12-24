package com.practica1.androidengine;

import android.graphics.Bitmap;

/*
 * La clase `AndroidImage` es una extensión de la clase `ImageJ` y representa un recurso de imagen
 * en una aplicación de Android. Encapsula un objeto `Bitmap` y proporciona métodos para
 * acceder al ancho, alto y al objeto `Bitmap` subyacente de la imagen.
 */

public class AndroidImage {
    String route;
    private Bitmap bitmap;

    public AndroidImage() {
    }

    public int getWidth() {
        return this.bitmap.getWidth();
    }

    public int getHeight() {
        return this.bitmap.getHeight();
    }

    public Bitmap getImage() {
        return bitmap;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
