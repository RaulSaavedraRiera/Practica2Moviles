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
    private int idActualLevel = 1;

    private int nWorld;
    final String path = "levels";
    final String BgPath = "sprites/backgrounds/";

    private ArrayList<String> files;
    private ArrayList<String> filesImage;
    private ArrayList<WorldStyle> worldStyles;
    private ArrayList<ArrayList<Level>> worlds;
    private Map<String, AndroidImage> backgrounds;
    private static ResourcesManager instance = null;
    private Level actualLevel;
    private Context currentContext;


    private void WorldManager() {
    }

    public void Init(AndroidEngine engine) {

        idActualWorld = 1;

        backgrounds = new HashMap<>();
        worlds = new ArrayList<ArrayList<Level>>();
        worldStyles = new ArrayList<WorldStyle>();
        files = new ArrayList<String>();
        filesImage = new ArrayList<String>();

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


    // Mundo actual en el que se encuentra el jugador
    public int getIdActualWorld() {
        return idActualWorld;
    }

    // Paso entre los mundos (necesario en la clase para saber que recursos cargar en cada momento)
    public boolean changeWorld(boolean add) {

        if (add && idActualWorld + 1 < nWorld + 1) {
            idActualWorld++;
            return true;
        } else if (!add && idActualWorld - 1 > 0) {
            idActualWorld--;
            return true;
        }
        return false;
    }


    // Obtiene el numero de niveles en un mundo
    public int getLevelsInWorld(int id) {
        if (id >= 0 && id < worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(id);
            return selectedLevels.size();
        } else {
            return -1;
        }
    }


    public int getSkinsId() {
        return worldStyles.get(idActualWorld - 1).getIdSkins();
    }

    // Obtiene un nivel especifico del mundo
    public Level getLevel(int levelIndex) {
        if (idActualWorld > 0 && idActualWorld - 1 <= worlds.size()) {
            ArrayList<Level> selectedLevels = worlds.get(idActualWorld - 1);
            if (levelIndex >= 0 && levelIndex < selectedLevels.size()) {
                setActualLevel(selectedLevels.get(levelIndex));
                return selectedLevels.get(levelIndex);
            }
        }
        return null;
    }

    // Devuelve el nivel en el que se encuentra el jugador
    public Level getActualLevel() {
        return actualLevel;
    }

    public void LoadImageFolders() {
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

    public ArrayList<AndroidImage> LoadLevelIcons(int id, AndroidGraphics g) {
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

    public ArrayList<AndroidImage> LoadGameIcons(AndroidGraphics graphics) {
        return LoadBoughtImages(graphics, "codigos");
    }

    public AndroidImage getBackground(AndroidGraphics graphics, boolean shop) {

        String aux = "";
        if(shop){
            Skin skin = ShopManager.getInstance().getActiveSkin("fondos");
            if(skin != null)
                aux = skin.getSkinsPath();
            else
                return null;

        }else {
            aux = worldStyles.get(idActualWorld - 1).getBackground();
        }

        if (backgrounds.containsKey(aux)) {
            return backgrounds.get(aux);
        } else {

            try {
                AndroidImage androidImage = graphics.createImage(aux);
                backgrounds.put(aux, androidImage);
                return androidImage;
            } catch (NullPointerException e) {
                return null;
            }
        }
    }

    private ArrayList<AndroidImage> LoadBoughtImages(AndroidGraphics g, String category) {
        if (ShopManager.getInstance().getActiveSkin(category) == null) {
            return null;
        }

        String skinPath = ShopManager.getInstance().getActiveSkin(category).getSkinsPath();
        ArrayList<AndroidImage> images = new ArrayList<>();
        AssetManager mngr = currentContext.getAssets();

        try {
            String[] directories = mngr.list(skinPath);
            for (String directory : directories) {
                AndroidImage i = g.createImage(skinPath + directory);
                images.add(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }


    // Resetamos para mostrar de nuevo el mundo 1 al entrar
    public void resetWorld() {
        idActualWorld = 1;
    }



    private void ReadLevels() {
        AssetManager mngr = currentContext.getAssets();

        try {
            for (String nameW : files) {
                String[] directories = mngr.list(path + '/' + nameW);

                ArrayList<Level> levels = new ArrayList<>();

                for (String directory : directories) {
                    Level l = JSONToLevel(mngr, path + '/' + nameW + '/' + directory);
                    if (l != null)
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
                WorldStyle w = new WorldStyle(BgPath + background, idSkin);
                worldStyles.add(w);
            } else {
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

    private void ReadWorlds() {
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

    public Context getContext() {
        return currentContext;
    }

    public int getNWorld() {
        return nWorld;
    }

    public int getIdActualLevel() {
        return idActualLevel;
    }

    public void setIdActualLevel(int idActualLevel) {
        this.idActualLevel = idActualLevel;
    }
}
