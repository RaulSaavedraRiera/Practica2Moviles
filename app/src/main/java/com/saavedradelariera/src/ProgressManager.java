package com.saavedradelariera.src;

import android.content.Context;
import android.util.Pair;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidFile;
import com.practica1.androidengine.NDKManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Map;

/*
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y guardar toda esta informacion
 * para poder ser usada por las demas clases.
 */
public class ProgressManager {
    private ResourcesManager resourcesManager = ResourcesManager.getInstance();
    private int worldPass = 0;
    private int levelPass = 0;
    private String levelState = "NONE", rowsInfo = "";
    private int currentWorld, currentLevelWorld, currentLevelDifficult;
    private int[] solutionInfo;
    private static ProgressManager instance = null;
    private Context context;
    private AndroidEngine engine;
    private String file = "progress.json";
    private String hashFile = "hash.txt";
    private int balance;
    private AndroidFile files = new AndroidFile();

    private void ProgressManager() {
    }

    public static ProgressManager getInstance() {
        if (instance == null) {
            instance = new ProgressManager();
        }
        return instance;
    }

    public void Init(AndroidEngine engine) {
        this.context = engine.getContext();
        this.engine = engine;
    }

    // Metodo encargado de guardar en json la informacion en ese momento del juego asi de crear un hash de esa informacion
    public void saveInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("level", levelPass);
            jsonObject.put("world", worldPass);
            jsonObject.put("balance", ShopManager.getInstance().getBalance());

            saveShop(jsonObject);

            if (SceneManager.getInstance() != null)
            {
                String s = SceneManager.getInstance().getActiveSceneState();
                if(s.length() > 0)
                    jsonObject.put("stateLevel", s);

            }

            FileOutputStream fileOutputStream = files.openFileOutput(file, context);
            OutputStreamWriter outputStreamWriter = files.createOutputStreamWriter(fileOutputStream);

            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();

            CreateHash(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    // Reinicia los valores iniciales del juego
    public void resetGame()
    {
        levelPass = 0;
        worldPass = 0;
        levelState = "NONE";
        ShopManager.getInstance().eraseData();
    }


    // Carga la informacion del json (si existe) relacionada con el estado del juego y comprueba que no se hayan producido modificaciones
    // externas en estos
    public void loadFromJSON() {
        try {
            String boughtIconsStr, boughtBackgroundsStr, boughtColorsStr, activeColor, activeBackground, activeIcon;
            FileInputStream fileInputStream = files.openFileInputStream(file, context);

            InputStreamReader inputStreamReader = files.createInputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            levelPass = jsonObject.getInt("level");
            worldPass = jsonObject.getInt("world");
            levelState = jsonObject.getString("stateLevel");
            balance = jsonObject.getInt("balance");

            if (jsonObject.has("activeBackground")) {
                activeBackground = jsonObject.getString("activeBackground");
                ShopManager.getInstance().setActiveSkin("fondos", activeBackground);
            }

            if (jsonObject.has("activeColor")) {
                activeColor = jsonObject.getString("activeColor");
                ShopManager.getInstance().setActiveSkin("colores", activeColor);
            }

            if (jsonObject.has("activeIcon")) {
                activeIcon = jsonObject.getString("activeIcon");
                ShopManager.getInstance().setActiveSkin("codigos", activeIcon);
            }

            if (jsonObject.has("boughtIcons")) {
                boughtIconsStr = jsonObject.getString("boughtIcons");
                ShopManager.getInstance().loadBoughtSkins("codigos", boughtIconsStr);
            }

            if (jsonObject.has("boughtBackgrounds")) {
                boughtBackgroundsStr = jsonObject.getString("boughtBackgrounds");
                ShopManager.getInstance().loadBoughtSkins("fondos", boughtBackgroundsStr);
            }

            if (jsonObject.has("boughtColors")) {
                boughtColorsStr = jsonObject.getString("boughtColors");
                ShopManager.getInstance().loadBoughtSkins("colores", boughtColorsStr);
            }

            ShopManager.getInstance().setBalance(balance);

            ProcessLevelInfo();

            String infoJSON = jsonObject.toString();
            boolean hashesMatch = CompareHash(infoJSON, hashFile);

            // Si vemos que ha modificado los archivos le reseteamos el progreso
            if(!hashesMatch)
            {
                resetGame();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    // Encargado de setear como pasado el nivel actual y desbloquear el siguiente nivel teniendo en cuenta el paso entre los mundos
    public void setLevelPass() {
        int levelsInCurrentWorld = resourcesManager.getLevelsInWorld(resourcesManager.getIdActualWorld());

        if(resourcesManager.getIdActualLevel() != levelPass)
            return;

        if(resourcesManager.getIdActualWorld() < worldPass)
            return;

        if (levelPass > levelsInCurrentWorld - 1)
            return;

        if (levelsInCurrentWorld - 1 == levelPass ) {
            if (resourcesManager.getIdActualWorld() <= resourcesManager.getNWorld()) {
                levelPass = 0;
                worldPass++;
            }
        } else
            levelPass++;
    }

    // Metodo encargado de obtener el siguiente nivel que jugara el jugador (no importa si esta pasado o no)
    public Pair<Integer, Integer> getNextLevelInfo() {
        int actLevel = resourcesManager.getIdActualLevel();
        int actWorld = resourcesManager.getIdActualWorld();

        int nLevels = resourcesManager.getLevelsInWorld(actWorld);

        //Si es el ultimo nivel que me he pasado
        if(actLevel == levelPass && actWorld == worldPass)
        {
            return Pair.create(levelPass, worldPass);
        }
        else
        {
            if(actLevel + 1 < nLevels)
                return Pair.create(actLevel+1, actWorld);
            else
            {
                if(actWorld + 1 > resourcesManager.getNWorld())
                    return Pair.create(-1, -1);
                else
                    return Pair.create(0, actWorld + 1);
            }

        }
    }

    // Obtener el nivel que se tiene que pasar el jugador
    public int getLevelPass() {
        return levelPass;
    }

    // Obtener el mundo que se tiene que pasar el jugador
    public int getWorldPass() {
        return worldPass;
    }

    // Obtener la pantalla de mundo donde esta el juagdor
    public int getIdActualWorld(){
        return resourcesManager.getIdActualWorld();
    }

    // Comprobamos si se ha dejado o no un nivel a medias
    public boolean levelInProgress(){
        return !levelState.equals("NONE");
    }

    // Comprobamos si se ha dejado un nivel a medias a partir del mundo
    public boolean WorldLevelInProgress(){
        return currentWorld != 99;
    }

    // Borramos informacion del nivel que se ha dejado a medias
    public void DeleteProgressInLevel(){
        levelState = "NONE";
    }

    // Obtenemos el id del mundo al que esta jugando el jugador
    public int getWorldInProgress(){
        return currentWorld;
    }

    // Obtenemos el id del nivel al que estaba jugando el jugador
    public int GetLevelInProgress(){
        return  currentLevelWorld;
    }

    // Obtenemos la dificultad del nivel al que estaba jugando el jugador
    public int getLevelInProgressDifficult(){
        return  currentLevelDifficult;
    }

    // Obtenemos la solucion del nivel que estaba jugando el jugador
    public int[] getLevelInProgressSolution(){
        return solutionInfo;
    }

    // Obtenemos la informacion de las filas del nivel que estaba jugando el jugador y borramos la informacion del nivel que se estaba pasando
    public String getLevelRowState() {
        levelState = "NONE";
        return rowsInfo;
    }

    // Encargado de convertir la informacion del estado del nivel (si hubiera) para poder usarla en el juego
    /* 0-1: mundo, 00 si es nivel rapido
     * 2-3: nivel, 00 si es nivel rapido
     * 4: dificultad
     * 5: tamaño solucion
     * 6-6+sizeof(tamañosolucion) solucion
     * x-x+3:numero de filas
     * x+3-y:movimientos realizados
     */
    private void ProcessLevelInfo(){
        if(!levelState.equals("NONE"))
        {
            currentWorld = Integer.parseInt(levelState.substring(0, 2));
            currentLevelWorld = Integer.parseInt(levelState.substring(2, 4));
            currentLevelDifficult = Character.getNumericValue(levelState.charAt(4));


            int solutionSize = Integer.parseInt(levelState.substring(5, 7));

            solutionInfo = new int[solutionSize];
            for (int i = 0; i < solutionSize; i++) {
                solutionInfo[i] = Character.getNumericValue(levelState.charAt(i+7));
            }

            int initRowInfo = 7+ solutionSize;

            rowsInfo = levelState.substring(initRowInfo, levelState.length());
        }
    }


    // A partir del ndkManager genera el hash de la informacion que se ha guardado en el json y se guarda en un archivo
    private void CreateHash(String infoJSON){

            String hash = engine.doGenerateHash(infoJSON);
            try {
                FileOutputStream fileOutputStream = files.openFileOutput(hashFile, context);
                OutputStreamWriter outputStreamWriter = files.createOutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(hash);
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    // Compara si dos archivos hash tienen la misma informacion
    private boolean CompareHash(String infoJSON, String hashFile) {

        String hash = engine.doGenerateHash(infoJSON);
        String storedHash = ReadStoredHash(hashFile);

        return storedHash != null && storedHash.equals(hash);

    }

    // Método para leer el hash almacenado previamente en un archivo
    private String ReadStoredHash(String hashFile) {
        try {
            FileInputStream fileInputStream = files.openFileInputStream(hashFile, context);
            InputStreamReader inputStreamReader = files.createInputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Metodo para guardar en el json toda la informacion necesaria para la tienda
    private void saveShop(JSONObject jsonObject) throws JSONException {
        if (ShopManager.getInstance().getActiveSkin("codigos") != null)
            jsonObject.put("activeIcon", ShopManager.getInstance().getActiveSkin("codigos").getTitle());

        if (ShopManager.getInstance().getActiveSkin("fondos") != null)
            jsonObject.put("activeBackground", ShopManager.getInstance().getActiveSkin("fondos").getTitle());

        if (ShopManager.getInstance().getActiveSkin("colores") != null)
            jsonObject.put("activeColor", ShopManager.getInstance().getActiveSkin("colores").getTitle());

        if (ShopManager.getInstance().getBoughtSkinsStr("codigos") != null)
            jsonObject.put("boughtIcons", ShopManager.getInstance().getBoughtSkinsStr("codigos"));

        if (ShopManager.getInstance().getBoughtSkinsStr("fondos") != null)
            jsonObject.put("boughtBackgrounds", ShopManager.getInstance().getBoughtSkinsStr("fondos"));

        if (ShopManager.getInstance().getBoughtSkinsStr("colores") != null)
            jsonObject.put("boughtColors", ShopManager.getInstance().getBoughtSkinsStr("colores"));
    }

}
