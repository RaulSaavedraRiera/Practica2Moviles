package com.practica1.androidengine.mobileManagers;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;

import androidx.core.app.ActivityCompat;

/*
 * Clase encargada de agregar los sensores necesarios
 */
public class SensorsMobile {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float accelerometerThreshold, timeBtwUses = 1;
    private long lastCallInSeconds;

    SensorEventListener activity;
    Context context;

    public SensorsMobile(){
    }

    //inicializa los sensores dada una aplicacion compatible con escucha de eventos
    public void initializateSensors(SensorEventListener activity, Context context){
        this.activity = activity;
        this.context =  context;

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

        //agregamos el sensor
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    //setea los parametros del acelerometro, necesario para que registre eventos
    public void setParamsAccelerometer(float accelerometerThreshold, float timeBtwUses)
    {
        this.accelerometerThreshold = accelerometerThreshold;
        this.timeBtwUses = timeBtwUses;
    }

    //activa sensores
    public void enableSensors(){
        if (accelerometer != null)
            sensorManager.registerListener(activity, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }

    //desactiva sensores
    public void disableSensors(){
        if (accelerometer != null)
            sensorManager.unregisterListener(activity);
    }

    //devuelve si el evento es un evento de acelerometro valido dado los parametros dados
    public boolean validAccelerometerEvent(SensorEvent event)
    {
        if(timeBtwUses == -1)
            return false;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long t = (System.currentTimeMillis() / 1000) - lastCallInSeconds;
            if (t >= timeBtwUses && (event.values[0] > accelerometerThreshold || event.values[1] > accelerometerThreshold || event.values[2] > accelerometerThreshold)) {
                lastCallInSeconds = System.currentTimeMillis();
                lastCallInSeconds /= 1000;
                return true;
            }
        }

        return false;
    }

    public SensorManager getSensorManager(){
        return sensorManager;
    }
    public Sensor getAccelerometer(){
        return accelerometer;
    }

}
