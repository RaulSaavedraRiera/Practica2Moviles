package com.saavedradelariera.src;

import android.content.Context;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidFile;
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
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y
 * guardar toda esta informacion para poder ser usada por las demas clases. Tambien guarda lo realacionado
 * a los iconos y fondos de los mundos y niveles.
 */
public class ResourcesManager {
    private int idActualWorld = 0;
    private int idActualLevel = 0;
    private int nWorld;
    final String LvlPath = "levels";
    final String BgPath = "sprites/backgrounds/";
    private ArrayList<String> files;
    private ArrayList<String> filesImage;
    private HashMap<Integer ,WorldStyle> worldStyles;
    private ArrayList<ArrayList<Level>> worlds;
    private Map<String, AndroidImage> backgrounds;
    private Map<Integer, ArrayList<AndroidImage>> iconsLoaded;
    private static ResourcesManager instance = null;
    private Level actualLevel;
    private Context currentContext;
    private AndroidFile filesAndroid = new AndroidFile();
    private int contStyle = 0;

    // Inicialilzacion del Singleton
    public void Init(AndroidEngine engine) {
        idActualWorld = 0;

        backgrounds = new HashMap<>();
        worlds = new ArrayList<ArrayList<Level>>();
        worldStyles = new HashMap<>();
        files = new ArrayList<String>();
        filesImage = new ArrayList<String>();
        iconsLoaded = new HashMap<>();

        currentContext = engine.getContext();
        readWorlds();
        readLevels();
        loadImageFolders();
    }

    public static ResourcesManager getInstance() {
        if (instance == null) {
            instance = new ResourcesManager();
        }
        return instance;
    }

    // Mundo actual en el que se encuentra el jugador visualmente
    public int getIdActualWorld() {
        return idActualWorld;
    }

    // Paso entre los mundos (necesario en la clase para saber que recursos cargar en cada momento)
    public boolean changeWorld(boolean add) {

        if (add && idActualWorld < nWorld - 1) {
            idActualWorld++;
            return true;
        } else if (!add && idActualWorld > 0) {
            idActualWorld--;
            return true;
        }
        return false;
    }

    // Setea el mundo en el que se encentra el jugador visualmente
    public boolean setWorld(int newWorld) {
        if (newWorld < 0 || newWorld > nWorld)
            return false;
        idActualWorld = newWorld;
        return true;
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

    // Obtiene el id de los iconos enlazados a un mundo en especifico
    public int getSkinsId() {
        if(worldStyles.containsKey(idActualWorld))
            return worldStyles.get(idActualWorld).getIdSkins();
        else
            return -1;
    }

    // Obtiene un nivel (Level) especifico de un mundo
    public Level getLevel(int levelIndex, int worldIndex) {
        if (worldIndex >= 0 && worldIndex <= worlds.size() - 1) {
            ArrayList<Level> selectedLevels = worlds.get(worldIndex);
            if (levelIndex >= 0 && levelIndex < selectedLevels.size()) {
                Level level = selectedLevels.get(levelIndex);
                this.actualLevel = level;
                return level;
            }
        }
        return null;
    }

    // Devuelve el nivel en el que se encuentra el jugador
    public Level getActualLevel() {
        return actualLevel;
    }

    // Metodo encargado de cargar las carpetas donde se encuentran los iconos para los niveles.
    // Aqui solo se guarda la ruta hacia la carpeta y cuando se necesario se cargaran los iconos
    public void loadImageFolders() {
        try {
            String[] directories = filesAndroid.listFiles(currentContext, "sprites/icons");

            for (String directory : directories) {
                filesImage.add("sprites/icons/" + directory);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Carga todos los iconos de las carpetas y los guarda para su posterior uso
    public ArrayList<AndroidImage> loadLevelIcons(int id, AndroidGraphics g) {
        ArrayList<AndroidImage> images = new ArrayList<>();

        try {

            if (iconsLoaded.containsKey(id))
                return iconsLoaded.get(id);

            String[] directories = filesAndroid.listFiles(currentContext, filesImage.get(id));
            for (String directory : directories) {
                AndroidImage i = g.createImage(filesImage.get(id) + "/" + directory);
                images.add(i);
            }
            iconsLoaded.put(id, images);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }


    // Carga los iconos de la tienda
    public ArrayList<AndroidImage> loadGameIcons(AndroidGraphics graphics) {
        return loadBoughtImages(graphics, "codigos");
    }

    // Obtiene el background que ha sido comprado (tienda) o el correspondiente al mundo actual
    public AndroidImage getBackground(AndroidGraphics graphics, boolean shop) {

        String aux = "";
        if (shop) {
            Skin skin = ShopManager.getInstance().getActiveSkin("fondos");
            if (skin != null)
                aux = skin.getSkinsPath();
            else
                return null;

        } else {
            if(worldStyles.containsKey(idActualWorld))
            {
                aux = worldStyles.get(idActualWorld).getBackground();
            }

            if(aux.equals("NONE"))
                return null;
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

    // Se encarga de cargar todas los iconos que se mostraran en la tienda
    private ArrayList<AndroidImage> loadBoughtImages(AndroidGraphics g, String category) {
        if (ShopManager.getInstance().getActiveSkin(category) == null) {
            return null;
        }
        String skinPath = ShopManager.getInstance().getActiveSkin(category).getSkinsPath();
        ArrayList<AndroidImage> images = new ArrayList<>();

        try {
            String[] directories = filesAndroid.listFiles(currentContext, skinPath);
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
        idActualWorld = 0;
    }

    // Leemos la informacion correspondiente a los niveles de cada mundo y lo guardamos para su posterior uso
    private void readLevels() {
        try {
            for (String nameW : files) {
                String[] directories = filesAndroid.listFiles(currentContext, LvlPath + '/' + nameW);
                ArrayList<Level> levels = new ArrayList<>();


                int tam = worldStyles.size();
                for (String directory : directories) {
                    Level l = jsonTolevel(LvlPath + '/' + nameW + '/' + directory);

                    if (l != null)
                        levels.add(l);
                }

                if(worldStyles.size() == tam)
                {
                    WorldStyle wS = new WorldStyle("NONE", -1);
                    worldStyles.put(contStyle, wS);
                    contStyle++;
                }
                worlds.add(levels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Leemos del json la informacion de los niveles
    private Level jsonTolevel(String filePath) {
        try {
            InputStream inputStream = filesAndroid.createInputStream(currentContext, filePath);
            InputStreamReader inputStreamReader = filesAndroid.createInputStreamReader(inputStream);
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
                worldStyles.put(contStyle, w);
                contStyle++;
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

    // Leemos y obtenemos todos los directorios de los mundos de juego
    private void readWorlds() {
        try {
            String[] directories = filesAndroid.listFiles(currentContext, LvlPath);

            for (String directory : directories) {
                files.add(directory);
                nWorld++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Devuelve el numero de mundos que hay
    public int getNWorld() {
        return nWorld;
    }

    // Devuelve el nivel que esta viendo el jugador
    public int getIdActualLevel() {
        return idActualLevel;
    }

    // Seteamos el nivel que verá el jugador, asi podemos setear y devolver toda la informacion relativa a este
    public void setIdActualLevel(int idActualLevel) {
        this.idActualLevel = idActualLevel;
    }
}
