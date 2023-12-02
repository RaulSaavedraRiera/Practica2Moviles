package com.practica1.androidengine.mobileManagers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class MobileShare {

    SurfaceView surfaceView;
    AppCompatActivity app;

    public MobileShare(SurfaceView surfaceView, AppCompatActivity activity) {

        this.surfaceView = surfaceView;
        this.app = activity;
    }

    public void shareImage(Bitmap bitmap, String mnsg)
    {
        String imageUri = MediaStore.Images.Media.insertImage(app.getApplicationContext().getContentResolver(), bitmap,
            "Description" , null);
        Uri uri = Uri. parse(imageUri);
        Intent shareIntent = new Intent(Intent. ACTION_SEND);
        shareIntent.setType( "image/*");
        shareIntent.putExtra(Intent. EXTRA_TEXT, mnsg );
        shareIntent.putExtra(Intent. EXTRA_STREAM, uri);
        this.app.startActivity(Intent. createChooser(shareIntent , "Share Image" ));
    }

}


