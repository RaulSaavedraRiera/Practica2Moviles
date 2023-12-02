package com.practica1.androidengine.mobileManagers;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.w3c.dom.Text;


public class Mobile {

    AppCompatActivity activity;
    AdView mAdView;
    Notifications mNotifications;
    MobileShare mobileShare;
    AdRequest adRequest;
    public Mobile(AppCompatActivity activity, SurfaceView surfaceView){

        this.activity = activity;

        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mNotifications = new Notifications(activity);
        mobileShare = new MobileShare(surfaceView, activity);
    }

    public void GenerateBanner(int adViewID){
        mAdView = activity.findViewById(adViewID);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void SolicitateNotification(int icon, String title, String body, String channelName){
        mNotifications.GenerateNotification( icon, title, body, channelName);
    }

    public void SolicitatShare(Bitmap bitmap, String mnsg){
        mobileShare.shareImage(bitmap, mnsg);
    }

}


