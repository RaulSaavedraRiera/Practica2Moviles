package com.practica1.androidengine;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.practica1.engine.Engine;

/**
 * Extension of the `Engine` class and is responsible for managing
 * the core functionality of a game engine in an Android application.
 */
public class AndroidEngine extends Engine {
    private SurfaceHolder holder;
    private SurfaceView myView;

    public AndroidEngine(SurfaceView surfaceView) {
        super();
        myView = surfaceView;
        this.holder = this.myView.getHolder();
        graphics = new AndroidGraphics(new Canvas(), holder, myView, 600, 1000, myView.getContext().getAssets());
        input = new InputAndroid(myView);
        audioSystem = new AndroidAudio(myView.getContext().getAssets());
    }
}
