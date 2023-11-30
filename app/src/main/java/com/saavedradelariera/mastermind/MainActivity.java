package com.saavedradelariera.mastermind;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;


import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.ResourcesManager;
import com.saavedradelariera.src.ShopManager;
import com.saavedradelariera.src.scenes.MenuScene;


public class MainActivity extends AppCompatActivity {
    AndroidEngine androidEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SurfaceView renderView = findViewById(R.id.surfaceView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        androidEngine = new AndroidEngine(renderView);

        androidEngine.GenerateMobile(this);
        androidEngine.GenerateBanner(R.id.adView);


        androidEngine.SolicitateNotification(R.drawable.ic_launcher_foreground, "jaje", "jeje", "R.string.channel_name");

      /*

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "mic";
            String description = "aaaa";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("mi canal", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this,getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "mi canal")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that fires when the user taps the notification.
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Log.e("TAG", "notificacion apunto");
        notificationManager.notify(0, builder.build());

        Log.e("TAG", "notificacionya");

        */


        ResourcesManager.getInstance().Init(androidEngine);
        SceneManager.getInstance().Init(androidEngine);
        ShopManager.getInstance().Init(androidEngine);
        MenuScene mS = new MenuScene();
        //WorldScene mS = new WorldScene();
        SceneManager.getInstance().SetScene(mS);
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
