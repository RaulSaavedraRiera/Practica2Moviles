package com.practica1.graphics;

public abstract class Graphics implements IGraphics{

    int tX;
    int tY;
    float scl;

    public Graphics(){
    };
    public void setScl(float scl) {
        this.scl = scl;
    }
    public void settX(int tX) {
        this.tX = tX;
    }
    public void settY(int tY) {
        this.tY = tY;
    }

    @Override
    public void rescale() {
        float w = GetWidth();
        float h = GetHeight();
        float scale = Math.min(w/GetWidthRelative(), h/GetHeightRelative());

        float transformX = (GetWidth() - (GetWidthRelative()* scale)) / 2;
        float transformY = (GetHeight() - (GetHeightRelative() * scale)) / 2;

        if (transformX > 0) {
            transformX = (GetWidth() / 2) - GetWidthRelative() * scale / 2;
        } else {
            transformX = 0;
        }

        if (transformY > 0) {
            transformY = (GetHeight() / 2) - GetHeightRelative() * scale / 2;
        } else {
            transformY = 0;
        }

        setScl(scale);
        settX((int)transformX);
        settY((int)transformY);

        doTranslate(transformX, transformY);
        doScale(scale);
    }

    @Override
    public int getTranslateY() {
        return tY;
    }

    @Override
    public int getTranslateX() {
        return tX;
    }

    @Override
    public float getScale() {
        return scl;
    }

    public void doTranslate(float tx, float ty) {};
    public void doScale(float scale) {};



}
