package com.practica1.androidengine.mobileManagers;

import android.graphics.Bitmap;

/**
 * Callback para cuando se haya completado la captura de pantalla
 */
public interface ScreenShootFinish {
    void doAction(Bitmap bitmap);
}
