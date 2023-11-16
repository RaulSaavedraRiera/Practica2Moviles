package com.practica1.desktopengine;

import com.practica1.input.IInput;
import com.practica1.input.TouchEvent;

import java.util.ArrayList;

import javax.swing.JFrame;


public class InputDesktop implements IInput {

    private InputHandlerDesktop ihDesktop;

    InputDesktop(JFrame view) {
        ihDesktop = new InputHandlerDesktop(view);
    }

    @Override
    public ArrayList<TouchEvent> getTouchEvent() {
        return ihDesktop.getTouchEvent();
    }
}
