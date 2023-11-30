package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y guardar toda esta informacion
 * para poder ser usada por las demas clases.
 */
public class ResourcesManager {
    private int idActualWorld = 1;
    private int nWorld;
    final String path = "levels";
    final String BgPath = "sprites/backgrounds/";

    private ArrayList<String> files = new ArrayList<String>();
    private ArrayList<String> filesImage = new ArrayList<String>();
    private ArrayList<WorldStyle> worldStyles = new ArrayList<WorldStyle>();
    private ArrayList<ArrayList<Level>> worlds = new ArrayList<ArrayList<Level>>();
    private Map<String, AndroidImage> backgrounds = new HashMap<>();
    private static ResourcesManager instance = null;
    private Level actualLevel;
    private Context currentContext;



    private void WorldManager(){}

    public void Init(AndroidEngine engine) {

        currentContext = engine.getContext();
        ReadWorlds();
        ReadLevels();
        LoadImageFolders();
    }
    public static ResourcesManager getInstance() {
        if (instance == null) {
            instance = new ResourcesManager();
        }
        return instance;
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


    public int getLevelInWorld(int id)
    {
        if (id >= 0 && id < worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(id);
            return selectedLevels.size();
        } else {
            return -1;
        }
    }

    public int getSkinsId(){
        return worldStyles.get(idActualWorld-1).getIdSkins();
    }

    public Level getLevel(int levelIndex) {
        if (idActualWorld > 0 && idActualWorld - 1 <= worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(idActualWorld-1);
            if (levelIndex >= 0 && levelIndex < selectedLevels.size()) {
                setActualLevel(selectedLevels.get(levelIndex));
                return selectedLevels.get(levelIndex);
            }
        }
        return null;
    }

    public Level getActualLevel() {
        return actualLevel;
    }

    public void LoadImageFolders()
    {
        ArrayList<AndroidImage> images = new ArrayList<>();
        AssetManager mngr = currentContext.getAssets();

        try {
            String[] directories = mngr.list("sprites/icons");

            for (String directory : directories) {
                filesImage.add("sprites/icons/" + directory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<AndroidImage> LoadImages(int id, AndroidGraphics g)
    {
        ArrayList<AndroidImage> images = new ArrayList<>();
        AssetManager mngr = currentContext.getAssets();

        try {
                String[] directories = mngr.list(filesImage.get(id));
                for (String directory : directories) {
                    AndroidImage i = g.createImage(filesImage.get(id) + "/" + directory);
                    images.add(i);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

    public void resetWorld()
    {
        idActualWorld = 1;
    }
    public AndroidImage getBackground(AndroidGraphics graphics)
    {
        String aux = worldStyles.get(idActualWorld-1).getBackground();
        if(backgrounds.containsKey(aux)){
            return backgrounds.get(aux);
        }else {
            AndroidImage androidImage = graphics.createImage(aux);
            backgrounds.put(aux, androidImage);
            return androidImage;
        }
    }

    private void ReadLevels()
    {
        AssetManager mngr = currentContext.getAssets();

        try {
            for(String nameW : files)
            {
                String[] directories = mngr.list(path+'/'+nameW);

                ArrayList<Level> levels = new ArrayList<>();

                for (String directory : directories) {
                    Level l = JSONToLevel(mngr, path + '/' + nameW + '/' + directory);
                    if(l != null)
                        levels.add(l);
                }
                worlds.add(levels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setActualLevel(Level actualLevel) {
        this.actualLevel = actualLevel;
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
            if (filePath.contains("style")) {
                String background = jsonObject.getString("background");
                int idSkin = jsonObject.getInt("skins");
                WorldStyle w = new WorldStyle(BgPath+ background, idSkin);
                worldStyles.add(w);
            }
            else {
                int codeSize = jsonObject.getInt("codeSize");
                int codeOpt = jsonObject.getInt("codeOpt");
                boolean repeat = jsonObject.getBoolean("repeat");
                int attempts = jsonObject.getInt("attempts");

                Level level = new Level(codeSize, codeOpt, repeat, attempts);
                return level;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void ReadWorlds()
    {
        AssetManager mngr = currentContext.getAssets();

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
}
