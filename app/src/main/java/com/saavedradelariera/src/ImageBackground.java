package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidFont;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;

//Clase para poner una imgen de fondo de pantalla
public class ImageBackground extends GameObject {

    AndroidImage img;
    public ImageBackground(AndroidImage img){
        super(0,0,0,0);
     this.img = img;
    }

     //Pone la imagen deseada de fondo
    @Override
    public void render(AndroidGraphics graphics) {
        graphics.setBackgroundImage(img);
    }

    @Override
    public void update(AndroidEngine iEngine, float deltaTime) {
    }
}
