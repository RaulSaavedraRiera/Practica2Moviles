package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;

import java.io.IOException;
import java.util.ArrayList;

public class Level {

    private int codeSize;
    private int codeOpt;
    private int attempts;
    private boolean repeat;

    Level(int codeSize, int codeOpt, boolean repeat, int attempts) {
        this.attempts = attempts;
        this.codeSize = codeSize;
        this.codeOpt = codeOpt;
        this.repeat = repeat;
    }

    public int getAttempts() {
        return attempts;
    }

    public int getCodeOpt() {
        return codeOpt;
    }

    public int getCodeSize() {
        return codeSize;
    }

    public boolean isRepeat() {
        return repeat;
    }
}