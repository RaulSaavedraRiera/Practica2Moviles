package com.practica1.androidengine.mobileManagers;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;

import com.practica1.androidengine.TouchEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
 * Clase encargada del control de los sensores propios de android
 */
public class SensorsMobile implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float accelerometerThreshold, timeBtwUses = 1;
    private long lastCallInSeconds;

    SensorEventListener activity;
    Context context;

    ArrayList<TouchEvent> events = new ArrayList<>();

    public SensorsMobile(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

        //agregamos el sensor
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    //setea los parametros del acelerometro, necesario para que registre eventos
    public void setParamsAccelerometer(float accelerometerThreshold) {
        this.accelerometerThreshold = accelerometerThreshold;
        this.timeBtwUses = timeBtwUses;
    }

    //activa sensores
    public void enableSensors() {
        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    //desactiva sensores
    public void disableSensors() {
        if (accelerometer != null)
            sensorManager.unregisterListener(this);
    }

    //devuelve si el evento es un evento de acelerometro valido dado los parametros dados
    boolean validAccelerometerEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            if ((event.values[0] > accelerometerThreshold || event.values[1] > accelerometerThreshold || event.values[2] > accelerometerThreshold)) {
                lastCallInSeconds = System.currentTimeMillis();
                lastCallInSeconds /= 1000;
                return true;
            }
        }

        return false;
    }

    //obtenemos los eventos originados por los sensores
    public ArrayList<TouchEvent> getEvents() {

        ArrayList tmp = events;
        events = new ArrayList<>();
        return tmp;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //comprobamos eventos de acelerometro
        if (validAccelerometerEvent(sensorEvent)) {
            TouchEvent shakeEvent = new TouchEvent();
            shakeEvent.setType(TouchEvent.TouchEventType.SHAKE);
            synchronized (events) {
                events.add(shakeEvent);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
