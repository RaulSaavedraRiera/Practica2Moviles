package com.practica1.androidengine.mobileManagers;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import androidx.annotation.NonNull;
import androidx.work.WorkRequest;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;


import java.util.concurrent.TimeUnit;

public class Mobile {

    AppCompatActivity app;
    AdView mAdView;
    MobileShare mobileShare;
    RewardedAd rewardedAd;

    Activity activity;

    public Mobile(AppCompatActivity app, SurfaceView surfaceView, Activity activity){

        this.app = app;
        this.activity = activity;

        MobileAds.initialize(app, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mobileShare = new MobileShare(surfaceView, app);
    }

    public void GenerateBanner(int adViewID){
        mAdView = app.findViewById(adViewID);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void SolicitateNotification(int icon, String title, String body, String channelName, int time, TimeUnit timeUnit){

        SetUpNotification(channelName);

        WorkRequest request = new  OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(time, timeUnit)
                .setInputData(new Data.Builder()
                        .putString("title", title)
                        .putString("text", body)
                        .putInt("icon", icon)
                        .putString("channel", channelName)
                        .putString("package", activity.getPackageName())
                        .build())
                .build();

        // Programa la tarea para ser ejecutada por el WorkManager
        WorkManager.getInstance().enqueue(request);
    }

    void SetUpNotification(String channelN){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // CharSequence name = "Mi Canal";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelN, channelN, importance);
            channel.setDescription("---");
            NotificationManager notificationManager = app.getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    public void SolicitateShare(Bitmap bitmap, String mnsg){
        mobileShare.shareImage(bitmap, mnsg);
    }

    // Método para cargar el anuncio recompensado
    public void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(app, "ca-app-pub-3940256099942544/5224354917", adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
                ServerSideVerificationOptions options = new ServerSideVerificationOptions
                        .Builder()
                        .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                        .build();
                rewardedAd.setServerSideVerificationOptions(options);
            }
        });


    }

    public void showRewardedAd(AdFinish adFinish) {
        if (rewardedAd != null) {
            app.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            adFinish.doAction();
                        }
                    });
                }
            });
        } else {
            Log.d("TAG", "The rewarded ad wasn't ready yet.");
        }
    }
}





