package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.scenes.WorldScene;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y guardar toda esta informacion
 * para poder ser usada por las demas clases.
 */
public class WorldManager {

    private int idActualWorld = 1;
    private int nWorld;
    final String path = "levels";
    private ArrayList<String> files = new ArrayList<String>();
    private ArrayList<ArrayList<Level>> worlds = new ArrayList<ArrayList<Level>>();
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

    private void ReadWorlds(Context c)
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
        return idActualWorld;
    }

    public boolean changeWorld(boolean add){

        if(add && idActualWorld + 1 < nWorld + 1)
        {
            idActualWorld++;
            return true;
        }else if (!add && idActualWorld - 1 > 0){
            idActualWorld--;
            return true;
        }
        return false;
    }

    private void ReadLevels(Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            for(String nameW : files)
            {
                String[] directories = mngr.list(path+'/'+nameW);

                ArrayList<Level> levels = new ArrayList<>();

                for (String directory : directories) {
                    Level l = JSONToLevel(mngr, path + '/' + nameW + '/' + directory);
                    levels.add(l);
                }
                worlds.add(levels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLevelInWorld(int id)
    {
        if (id >= 0 && id < worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(id);
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
        scenesMap.put(idActualWorld, wS);
    }

    public WorldScene getWorldScene()
    {
        return scenesMap.get(idActualWorld);
    }

    private Level JSONToLevel(AssetManager mngr, String filePath) {
        try {
            InputStream inputStream = mngr.open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            String json = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(json);

            int dif = jsonObject.getInt("dif");
            boolean lock = jsonObject.getBoolean("locked");

            Level level = new Level(dif, lock);

            return level;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Level getLevel(int levelIndex) {
        if (idActualWorld >= 0 && idActualWorld <= worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(idActualWorld);
            if (levelIndex >= 0 && levelIndex < selectedLevels.size()) {
                return selectedLevels.get(levelIndex);
            }
        }
        return null;
    }

}
