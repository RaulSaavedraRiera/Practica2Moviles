package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.scenes.WorldScene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorldManager {

    private int idActualWordl = 1;
    private int nWorld;
    final String path = "levels";
    private ArrayList<String> files = new ArrayList<String>();
    private ArrayList<ArrayList<Level>> level = new ArrayList<ArrayList<Level>>();
    private static WorldManager instance = null;
    private Map<Integer, WorldScene> scenesMap = new HashMap<>();

    private void WorldManager(){}

    public void Init(AndroidEngine engine) {

        ReadWorlds(engine.getContext());
        ReadLevels(engine.getContext());
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

    public void ReadLevels(Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            for(String nameW : files)
            {
                String[] directories = mngr.list(path+'/'+nameW);

                ArrayList<Level> levels = new ArrayList<>();

                for (String directory : directories) {
                    Level l = new Level();
                    levels.add(l);
                }
                level.add(levels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLevelInWorld(int id)
    {
        if (id >= 0 && id < level.size()) {
            ArrayList<Level> selectedLevels = level.get(id);
            return selectedLevels.size();
        } else {
            return -1;
        }
    }

    public boolean isSceneCreated(int id)
    {
        return scenesMap.containsKey(id);
    }

    public void addScene(WorldScene wS)
    {
        scenesMap.put(idActualWordl, wS);
    }

    public WorldScene getWorldScene()
    {
        return scenesMap.get(idActualWordl);
    }

}
