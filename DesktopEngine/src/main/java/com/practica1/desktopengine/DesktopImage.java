package com.practica1.desktopengine;


import com.practica1.graphics.ImageJ;

import java.awt.Image;


/**
 * Clase encargada para el uso de Imagenes en Desktop
 *
 * Esta solo se encargara de guardar la Image
 * Lo usamos por si necesitamos meter más datos pero con la implementacion actual solo guardamos este.
 */
public class DesktopImage extends ImageJ {
    private Image image;

    public DesktopImage() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
