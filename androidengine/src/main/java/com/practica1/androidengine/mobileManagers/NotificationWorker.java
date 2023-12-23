package com.practica1.androidengine.mobileManagers;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


/**
 * Encargado de la creacion del Worker para las notificaciones
 */
public class NotificationWorker extends Worker {

    NotificationCompat.Builder notificationBuilder;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("title");
        String text = getInputData().getString("text");
        int icon = getInputData().getInt("icon", 1);
        String channel = getInputData().getString("channel");
        String packageName = getInputData().getString("package");

        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Configuramos la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            // Si no tenemos permisos, no podremos hacer nada
            Log.d("NOTIFICATION_WORKER", "Notificacion NO mandada desde el worker");
            return Result.failure();
        }
        notificationManager.notify(1, builder.build());
        Log.d("NOTIFICATION_WORKER", "Notificacion mandada desde el worker");
        return Result.success();
    }
}
