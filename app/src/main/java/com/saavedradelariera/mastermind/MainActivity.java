package com.saavedradelariera.mastermind;

import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.tv.AdRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.scenes.MenuScene;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    AndroidEngine androidEngine;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private final float SENSORTHRESHOLD = 17f, TIMEBTWUSES = 1F;
    private long lastCallInSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SurfaceView renderView = findViewById(R.id.surfaceView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        androidEngine = new AndroidEngine(renderView);

        androidEngine.GenerateMobile(this, MainActivity.this);
        androidEngine.GenerateBanner(R.id.adView);
        androidEngine.SolicitateLoadRewardAd();


        System.loadLibrary("androidEngine");

        ResourcesManager.getInstance().Init(androidEngine);
        ProgressManager.getInstance().Init(androidEngine.getContext());
        SceneManager.getInstance().Init(androidEngine);

        ShopManager.getInstance().init(androidEngine);
        ProgressManager.getInstance().loadFromJSON();

        MenuScene mS = new MenuScene();
        SceneManager.getInstance().SetScene(mS);

        //agregamos el sensor
        sensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressManager.getInstance().loadFromJSON();
        androidEngine.Resume();

        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ProgressManager.getInstance().saveInJSON();
        androidEngine.SolicitateNotification(R.drawable.ic_launcher_foreground, "Mastermind", "¡Entra a jugar y no te pierdas los nuevos niveles!", "canalmaster", 10, TimeUnit.SECONDS);
        androidEngine.Pause();

        if (accelerometer != null)
            sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long t = (System.currentTimeMillis() / 1000) - lastCallInSeconds;
            if (t >= TIMEBTWUSES && (event.values[0] > SENSORTHRESHOLD || event.values[1] > SENSORTHRESHOLD || event.values[2] > SENSORTHRESHOLD)) {
                SceneManager.getInstance().LaunchAcceleratorEvent();
                System.out.println("lanza bolita!");
                lastCallInSeconds = System.currentTimeMillis();
                lastCallInSeconds /= 1000;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
