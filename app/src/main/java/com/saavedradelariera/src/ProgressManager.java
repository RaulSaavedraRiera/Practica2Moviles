package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.AndroidImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de leer los directorios de los mundos, asi como de leer los json de los niveles y guardar toda esta informacion
 * para poder ser usada por las demas clases.
 */
public class ProgressManager {
    private ResourcesManager resourcesManager = ResourcesManager.getInstance();
    private int worldPass = 1;
    private int levelPass = 1;

    private String levelState = "", rowsInfo = "";
    private int[] solutionInfo;


    private static ProgressManager instance = null;

    private Context context;

    private String file = "progress.json";

    private void ProgressManager() {
    }

    public static ProgressManager getInstance() {
        if (instance == null) {
            instance = new ProgressManager();
        }
        return instance;
    }

    public void Init(Context context)
    {
        this.context = context;
    }

    public void saveInJSON()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("level", levelPass);
            jsonObject.put("world", worldPass);
            if(SceneManager.getInstance() != null)
                jsonObject.put("stateLevel", SceneManager.getInstance().GetActiveSceneState());

            FileOutputStream fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

            outputStreamWriter.write(jsonObject.toString());
            outputStreamWriter.close();

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }


    public void resetGame()
    {
        levelPass = 1;
        worldPass = 1;
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


            ProcessLevelInfo();


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLevelPass() {
        int levelsInCurrentWorld = resourcesManager.getLevelsInWorld(resourcesManager.getIdActualWorld() - 1);

        if(resourcesManager.getIdActualLevel() + 1 != levelPass)
            return;

        if(resourcesManager.getIdActualWorld() < worldPass)
            return;

        if (levelPass > levelsInCurrentWorld)
            return;

        if (levelsInCurrentWorld == levelPass ) {
            if (resourcesManager.getIdActualWorld() + 1 <= resourcesManager.getnWorld()) {
                levelPass = 1;
                worldPass++;
            }
        } else
            levelPass++;

    }


    void ProcessLevelInfo(){
        if(levelState != "")
        {
            int solutionSize = Character.getNumericValue(levelState.charAt(1));
            int initRowInfo = solutionSize+3;

            solutionInfo = new int[solutionSize];
            for (int i = 0; i < solutionSize; i++) {
                solutionInfo[i] = Character.getNumericValue(levelState.charAt(i+2));
            }

            rowsInfo = levelState.substring(initRowInfo, levelState.length());
        }
    }

    public int getLevelPass() {
        return levelPass;
    }

    public int getWorldPass() {
        return worldPass;
    }

    public int getIdActualWorld()
    {
        return resourcesManager.getIdActualWorld();
    }

    public boolean levelInProgress(){
        return levelState != "";
    }

    public void DeleteProgressInLevel(){
        levelState = "";
    }
    public int getLevelInProgressDifficult(){
       return  Integer.valueOf(levelState.substring(0,1));
    }

    public int[] getLevelInProgressSolution(){
        return solutionInfo;
    }
    public String getLevelRowState() {
        levelState = "";

       return rowsInfo;
    }

}
