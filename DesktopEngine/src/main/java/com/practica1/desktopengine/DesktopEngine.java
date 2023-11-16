package com.practica1.desktopengine;


import com.practica1.engine.Engine;

import javax.swing.JFrame;

/**
 * Engine correspondiente a la clase de Desktop
 */
public class DesktopEngine extends Engine {
    public DesktopEngine(JFrame myView, int rW, int rH) {
        super();
        graphics = new DesktopGraphics(myView, rW, rH);
        input = new InputDesktop(myView);
        audioSystem = new DesktopAudio();

    }
}
