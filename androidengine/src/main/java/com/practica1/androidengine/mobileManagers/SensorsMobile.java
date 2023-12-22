package com.practica1.androidengine.mobileManagers;

import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.Sensor;

public class SensorsMobile {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    Context context;

    public SensorsMobile(Context context){
        this.context = context;

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);

        //agregamos el sensor
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public SensorManager getSensorManager(){
        return sensorManager;
    }
    public Sensor getAccelerometer(){
        return accelerometer;
    }

}
