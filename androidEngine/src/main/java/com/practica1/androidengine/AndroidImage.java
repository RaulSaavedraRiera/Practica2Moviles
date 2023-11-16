package com.practica1.androidengine;

import android.graphics.Bitmap;

import com.practica1.graphics.ImageJ;

/**
 * The `AndroidImage` class is an extension of the `ImageJ` class and represents an image
 * resource in an Android application. It encapsulates a `Bitmap` and provides methods to
 * access the image's width, height, and the underlying `Bitmap` object.
 */
public class AndroidImage extends ImageJ {
    private Bitmap bitmap;
    AndroidImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public int GetWidth() {
        return this.bitmap.getWidth();
    }

    @Override
    public int GetHeight() {
        return this.bitmap.getHeight();
    }

    public Bitmap getImage() {
        return bitmap;
    }

}
