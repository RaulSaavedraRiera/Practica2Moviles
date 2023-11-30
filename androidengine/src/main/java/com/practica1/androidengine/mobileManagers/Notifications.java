package com.practica1.androidengine.mobileManagers;


import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

public class Notifications {

    NotificationCompat.Builder notificationBuilder;
    AppCompatActivity app;

    Notifications(AppCompatActivity app) {
        this.app = app;
    }

    public void GenerateNotification(int icon, String title, String body, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           // CharSequence name = "Mi Canal";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("mi canal", name, importance);
            channel.setDescription("Descripción del canal");
            NotificationManager notificationManager = app.getApplicationContext().getSystemService(NotificationManager.class);

            notificationManager.createNotificationChannel(channel);
        }


        Intent intent = new Intent(app.getApplicationContext() , app.getClass());
        PendingIntent contentIntent = PendingIntent.getActivity(app, 1, intent,
                PendingIntent.FLAG_IMMUTABLE);


// Configurar la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(app.getApplicationContext(), "mi canal")
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);  // Cierra la notificación al tocarla


        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(app);

        if (ActivityCompat.checkSelfPermission(app.getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            /*intent = new Intent();
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", app.getPackageName());
            intent.putExtra("app_uid", app.getApplicationInfo().uid);
            app.startActivity(intent);*/

            return;

        }
        else
        {
            Log.e("TAG", "notifiacion enviada");
            notificationManager.notify(1, builder.build());
        }

    }


}
