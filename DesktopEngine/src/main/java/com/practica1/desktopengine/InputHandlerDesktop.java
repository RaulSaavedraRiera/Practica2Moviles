package com.practica1.desktopengine;

import com.practica1.input.TouchEvent;
import com.practica1.pool.Pool;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Clase que obtiene los input correspondientes a dekstop
 */
public class InputHandlerDesktop implements MouseListener {

    private ArrayList<TouchEvent> events;
    private ArrayList<TouchEvent> eventsPending;
    private Pool pool;

    public InputHandlerDesktop(JFrame view) {
        view.addMouseListener(this);

        //Inicializamos los Array que se utilizan para guardar los eventos asi como la pool
        events = new ArrayList<TouchEvent>();
        eventsPending = new ArrayList<TouchEvent>();
        pool = new Pool(30);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent event =  pool.newEvent();

        event.setX(mouseEvent.getX());
        event.setY(mouseEvent.getY());
        event.setType(TouchEvent.TouchEventType.TOUCH_DOWN);

        synchronized (this) {
            eventsPending.add(event);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent event =  pool.newEvent();

        event.setX(mouseEvent.getX());
        event.setY(mouseEvent.getY());
             event.setType(TouchEvent.TouchEventType.TOUCH_UP);

        synchronized (this) {
            eventsPending.add(event);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }


    // Liberamos los eventos
    private void freeEvents(ArrayList<TouchEvent> event) {
        for (TouchEvent e : event) {
            pool.freeEvent(e);
        }
        event.clear();
    }


    // Obtenemos los eventos pendientes
    public synchronized ArrayList<TouchEvent> getTouchEvent() {

        freeEvents(this.events);
        this.events.addAll(this.eventsPending);
        eventsPending.clear();
        return events;
    }

}
