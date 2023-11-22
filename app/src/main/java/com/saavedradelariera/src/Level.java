package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;

import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private boolean locked;

    private int difficult;

    Level(int dif, boolean locked) {
        this.difficult = dif;
        this.locked = locked;
    }

    public int getDifficult() {
        return difficult;
    }

    public boolean isLocked() {
        return locked;
    }
}