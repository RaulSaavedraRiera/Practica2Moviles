package com.saavedradelariera.src;


import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidFont;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;
import com.practica1.androidengine.ColorJ;

//Clase text para dibujar textos
public class ImageBackground extends GameObject {

    AndroidImage img;

    //Inicialización de un Text con fuente específica
    public ImageBackground(AndroidImage img){
        super(0,0,0,0);

     this.img = img;
    }

     //Renderiza el texto con la fuente default o una especifica
    @Override
    public void Render(AndroidGraphics graphics) {
        graphics.setBackgroundImage(img);
    }

    @Override
    public void Update(AndroidEngine iEngine, float deltaTime) {
    }
}
