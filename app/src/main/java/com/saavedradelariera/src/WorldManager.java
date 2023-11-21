package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import java.io.IOException;
import java.util.ArrayList;

public class WorldManager {

    private int idActualWordl = 1;
    private int nWorld;
    final String path = "levels";
    private ArrayList<String> files = new ArrayList<String>();
    private static WorldManager instance = null;

    private void WorldManager(){}

    public void Init(AndroidEngine engine) {

        ReadWorlds(engine.getContext());

    }
    public static WorldManager getInstance() {
        if (instance == null) {
            instance = new WorldManager();
        }
        return instance;
    }

    public void ReadWorlds(Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            String[] directories = mngr.list(path);

            for (String directory : directories) {
                    files.add(directory);
                    nWorld++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getIdActualWordl() {
        return idActualWordl;
    }

    public boolean changeWorld(boolean add){

        if(add && idActualWordl + 1 < nWorld + 1)
        {
            idActualWordl++;
            return true;
        }else if (!add && idActualWordl - 1 > 0){
            idActualWordl--;
            return true;
        }
        return false;
    }
}
