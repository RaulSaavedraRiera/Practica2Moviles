package com.practica1.androidengine;

import android.view.SurfaceView;

import com.practica1.input.IInput;
import com.practica1.input.TouchEvent;

import java.util.ArrayList;

/**
 * The `InputAndroid` class provides an implementation of the `IInput` interface
 * for handling touch events on a `SurfaceView` in an Android application.
 */
public class InputAndroid implements IInput {
    private InputHandlerAndroid handler;
    public InputAndroid(SurfaceView view) {
        handler = new InputHandlerAndroid(view);
    }

    public ArrayList<TouchEvent> getTouchEvent() {
        return handler.getTouchEvent();
    }
}
