package com.practica1.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class InputHandlerAndroid implements View.OnTouchListener {

    private ArrayList<TouchEvent> events;
    private ArrayList<TouchEvent> pendingEvents;
    private Pool pool;

    public InputHandlerAndroid(SurfaceView view) {
        events = new ArrayList<TouchEvent>();
        pendingEvents = new ArrayList<TouchEvent>();
        pool = new Pool(30);
        view.setOnTouchListener(this);
    }


    /*
     * Maneja eventos táctiles y los convierte en objetos TouchEvent para su procesamiento.
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchEvent event = pool.newEvent();
        ;

        int action = motionEvent.getActionMasked();
        event.setX((int) motionEvent.getX());
        event.setY((int) motionEvent.getY());

        if (action == MotionEvent.ACTION_DOWN) {
            event.setType(TouchEvent.TouchEventType.TOUCH_DOWN);

        } else if (action == MotionEvent.ACTION_UP) {
            event.setType(TouchEvent.TouchEventType.TOUCH_UP);
        } else if (action == MotionEvent.ACTION_MOVE) {
            event.setType(TouchEvent.TouchEventType.DRAG);
        } else {
            event.setType(TouchEvent.TouchEventType.NONE);
        }

        synchronized (this) {
            pendingEvents.add(event);
        }

        return true;
    }

    /*
     * Recupera la lista de eventos táctiles y borra los eventos pendientes.
     */
    public synchronized ArrayList<TouchEvent> getTouchEvent() {
        freeEvents(this.events);
        this.events.addAll(this.pendingEvents);
        this.pendingEvents.clear();
        return events;
    }

    /*
     * Libera los objetos TouchEvent en el ArrayList proporcionado y borra la lista.     * @param event The ArrayList of TouchEvent objects to be cleared and freed.
     */
    private void freeEvents(ArrayList<TouchEvent> event) {
        for (TouchEvent e : event) {
            pool.freeEvent(e);
        }
        event.clear();
    }
}
