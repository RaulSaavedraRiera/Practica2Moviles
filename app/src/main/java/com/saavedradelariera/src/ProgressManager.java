package com.saavedradelariera.src;

import android.content.Context;
import android.util.Pair;

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

/**
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y guardar toda esta informacion
 * para poder ser usada por las demas clases.
 */
public class ProgressManager {
    private ResourcesManager resourcesManager = ResourcesManager.getInstance();
    private int worldPass = 0;
    private int levelPass = 0;

    //guardar informacion de nivel actual
    private String levelState = "NONE", rowsInfo = "";
    private int currentWorld, currentLevelWorld, currentLevelDifficult;
    private int[] solutionInfo;

    private static ProgressManager instance = null;
    private Context context;
    private String file = "progress.json";
    private String hashFile = "hash.txt";
    private int balance;
    private Map<String, Skin> activeSkinsMap;
    private ArrayList<JSONObject> jsonActiveSkinsMap;
    NDKManager ndkManager = new NDKManager();
    private void ProgressManager() {
    }

    public static ProgressManager getInstance() {
        if (instance == null) {
            instance = new ProgressManager();
        }
        return instance;
    }

    public void Init(Context context) {
        this.context = context;
    }

    public void saveInJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("level", levelPass);
            jsonObject.put("world", worldPass);
            jsonObject.put("balance", balance);

            if (SceneManager.getInstance() != null)
            {
                String s = SceneManager.getInstance().GetActiveSceneState();
                if(s.length() > 0)
                    jsonObject.put("stateLevel", s);

            }

            FileOutputStream fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();

            CreateHash(jsonObject.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGame()
    {
        levelPass = 0;
        worldPass = 0;
        levelState = "NONE";
    }

    public void loadFromJSON() {
        try {
            FileInputStream fileInputStream = context.openFileInput(file);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
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

            ProcessLevelInfo();

            String infoJSON = jsonObject.toString();
            boolean hashesMatch = CompareHash(infoJSON, hashFile);

            // Si vemos que ha modificado los archivos le reseteamos el progreso
            if(!hashesMatch)
            {
                levelPass = 0;
                worldPass = 0;
                levelState = "NONE";
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

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

    /* 0-1: mundo, 00 si es nivel rapido
     * 2-3: nivel, 00 si es nivel rapido
     * 4: dificultad
     * 5: tamaño solucion
     * 6-6+sizeof(tamañosolucion) solucion
     * x-x+3:numero de filas
     * x+3-y:movimientos realizados
    */
    void ProcessLevelInfo(){
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

            int initRowInfo = 7+solutionSize;

            rowsInfo = levelState.substring(initRowInfo, levelState.length());
        }
    }

    void CreateHash(String infoJSON){
        if (ndkManager != null) {
            String hash = ndkManager.generateHash(infoJSON);
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(hashFile, Context.MODE_PRIVATE);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.write(hash);
                outputStreamWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean CompareHash(String infoJSON, String hashFile) {

        String hash = ndkManager.generateHash(infoJSON);
        String storedHash = ReadStoredHash(hashFile);

        return storedHash != null && storedHash.equals(hash);

    }

    // Método para leer el hash almacenado previamente en un archivo
    private String ReadStoredHash(String hashFile) {
        try {
            FileInputStream fileInputStream = context.openFileInput(hashFile);

            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
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

    public int getLevelPass() {
        return levelPass;
    }
    public int getWorldPass() {
        return worldPass;
    }
    public int getIdActualWorld(){
        return resourcesManager.getIdActualWorld();
    }

    public boolean levelInProgress(){
        return !levelState.equals("NONE");
    }

    public boolean WorldLevelInProgress(){
        return currentWorld != 99;
    }

    public void DeleteProgressInLevel(){
        levelState = "NONE";
    }

    public int getWorldInProgress(){
        return currentWorld;
    }
    public int GetLevelInProgress(){
        return  currentLevelWorld;
    }
    public int getLevelInProgressDifficult(){
       return  currentLevelDifficult;
    }

    public int[] getLevelInProgressSolution(){
        return solutionInfo;
    }

    public String getLevelRowState() {
        levelState = "NONE";
       return rowsInfo;
    }

    public Pair<Integer, Integer> getNextLevelInfo() {
        int actLevel = resourcesManager.getIdActualLevel();
        int actWorld = resourcesManager.getIdActualWorld();

        int nLevels = resourcesManager.getLevelsInWorld(actWorld);

        //Si es el ultimo nivel que me he pasado
        if(actLevel == levelPass && actWorld == worldPass)
        {
            //setLevelPass();
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
}
