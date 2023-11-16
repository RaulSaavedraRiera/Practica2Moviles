package com.practica1.graphics;

public class ImageJ implements IImage {

    String route;
    public ImageJ()
    {}

    @Override
    public int GetWidth() {
        return 0;
    }

    @Override
    public int GetHeight() {
        return 0;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
