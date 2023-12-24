package com.practica1.androidengine;

import android.view.SurfaceView;

import java.util.ArrayList;

/*
 * La clase `InputAndroid` proporciona una implementación de la interfaz `IInput`
 * para manejar eventos táctiles en un `SurfaceView` en una aplicación de Android.
 */
public class InputAndroid {
    private InputHandlerAndroid handler;

    public InputAndroid(SurfaceView view) {
        handler = new InputHandlerAndroid(view);
    }

    public ArrayList<TouchEvent> getTouchEvent() {
        return handler.getTouchEvent();
    }
}
