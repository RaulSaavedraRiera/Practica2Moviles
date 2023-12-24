package com.saavedradelariera.mastermind;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.ProgressManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.scenes.DeleteScene;
import com.saavedradelariera.src.scenes.MenuScene;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements  SensorEventListener {
    AndroidEngine androidEngine;

    private boolean enterNotification = true;

    float accelerometerColdown = 1f, acceleromeThreshold = 17f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent.getCategories().contains(Intent.CATEGORY_LAUNCHER)){
            enterNotification = true;
        }
        setContentView(R.layout.activity_main);
        SurfaceView renderView = findViewById(R.id.surfaceView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        androidEngine = new AndroidEngine(renderView);
        androidEngine.generateMobile(this, MainActivity.this);
        androidEngine.generateBanner(R.id.adView);
        androidEngine.solicitateLoadRewardAd();
        androidEngine.loadLibraries();
        androidEngine.getSensors().initializateSensors(this);
        androidEngine.getSensors().setParamsAccelerometer(acceleromeThreshold, accelerometerColdown);


        ResourcesManager.getInstance().Init(androidEngine);
        ProgressManager.getInstance().init(androidEngine);
        SceneManager.getInstance().init(androidEngine);
        ShopManager.getInstance().init(androidEngine);
        ProgressManager.getInstance().loadFromJSON();
        MenuScene mS = new MenuScene();
        SceneManager.getInstance().setScene(mS);


       if(enterNotification) {
           ShopManager.getInstance().addBalance(100);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressManager.getInstance().loadFromJSON();
        androidEngine.resume();
        androidEngine.destroyNotification();
        androidEngine.getSensors().enableSensors();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ProgressManager.getInstance().saveInJSON();
        androidEngine.pause();
        androidEngine.getSensors().disableSensors();
    }

    @Override
    protected void onStop() {
        super.onStop();
        androidEngine.SolicitateNotification(R.drawable.ic_launcher_foreground,
                "Mastermind", "¡Entra a jugar y no te pierdas los nuevos niveles!", "canalmaster", 10, TimeUnit.SECONDS);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(androidEngine.getSensors().validAccelerometerEvent(event))
        {
            SceneManager.getInstance().launchAcceleratorEvent();
            System.out.println("lanza bolita!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        if(intent.getCategories().contains(Intent.CATEGORY_LAUNCHER))
            ShopManager.getInstance().addBalance(10);
        }
    }

