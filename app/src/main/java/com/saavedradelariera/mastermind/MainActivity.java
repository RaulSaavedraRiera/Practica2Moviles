package com.saavedradelariera.mastermind;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.WorldManager;
import com.saavedradelariera.src.scenes.MenuScene;
import com.saavedradelariera.src.scenes.WorldScene;

public class MainActivity extends AppCompatActivity {
    AndroidEngine androidEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        SurfaceView renderView = new SurfaceView(this);
        setContentView(renderView);
        androidEngine = new AndroidEngine(renderView);

        SceneManager.getInstance().Init(androidEngine);
        MenuScene mS = new MenuScene();
        //WorldScene mS = new WorldScene();
        SceneManager.getInstance().SetScene(mS);

        WorldManager.getInstance().Init(androidEngine.GetGraphics());
        //androidEngine.Resume();

    }

    @Override
    protected void onResume() {
        super.onResume();
        androidEngine.Resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        androidEngine.Pause();
    }
}
