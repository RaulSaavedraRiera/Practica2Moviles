package com.practica1.androidengine;

import android.graphics.Bitmap;

import com.practica1.graphics.ImageJ;

/**
 * The `AndroidImage` class is an extension of the `ImageJ` class and represents an image
 * resource in an Android application. It encapsulates a `Bitmap` and provides methods to
 * access the image's width, height, and the underlying `Bitmap` object.
 */
public class AndroidImage {

    String route;
    private Bitmap bitmap;
    AndroidImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public int GetWidth() {
        return this.bitmap.getWidth();
    }


    public int GetHeight() {
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

}
