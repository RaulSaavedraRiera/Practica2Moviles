package com.saavedradelariera.src;

import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.scenes.Scene;

import java.io.IOException;
import java.util.ArrayList;

/*Clase que gestiona las escenas del juego para facilitar su funcionamiento y relacion con el motor de
de tecnología. Permite lanzar nuevas ecenas, mandar mensajes y añadir objetos a la escena*/
public class WorldManager {

    private int idActualWordl;

    private int nWorld;

    private ArrayList<String> files = new ArrayList<String>();
    private static WorldManager instance = null;

    private AndroidGraphics androidGraphics;
    private AssetManager assetManager;

    private void WorldManager(){}

    public void Init(AndroidGraphics graphics) {
        androidGraphics = graphics;
        assetManager = graphics.getAssets();

        ReadWorlds();
    }
    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    public void ReadWorlds()
    {
        try {
            String[] directories = assetManager.list("levels/");

            // Procesar los nombres obtenidos (directorios) y guardarlos en el ArrayList
            for (String directory : directories) {

                    files.add(directory);
                    nWorld++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int a = 0;

    }

}
