package com.saavedradelariera.src;


/**
 * Clase encargada de guardar la informacion de cada uno de los niveles
 */

public class Level {

    private int codeSize, codeOpt, attempts;
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