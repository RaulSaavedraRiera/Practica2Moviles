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

public class ShopManager {
    private ArrayList<Skin> backgrounds;

    //private ArrayList<Skin> icons;
    final String path = "store";
    private ArrayList<String> files = new ArrayList<String>();

    public Map<String, ArrayList<Skin>> skinMap = new HashMap<>();

    private static ShopManager instance = null;

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    public void Init(AndroidEngine engine) {
        ReadCategories(engine.getContext());
        ReadSkins(engine.getContext());
    }

    private void ReadCategories (Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            String[] directories = mngr.list(path);

            for (String directory : directories) {
                files.add(directory);
                //nWorld++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Skin JSONToSkin(AssetManager mngr, String filePath) {
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

            String title = jsonObject.getString("title");
            String path = jsonObject.getString("path");
            int price = jsonObject.getInt("price");

            Skin skin = new Skin(title, price, path);

            return skin;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void ReadSkins(Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            for(String category : files)
            {
                String[] directories = mngr.list(path+'/'+category);

                for (String directory : directories) {
                    Skin skin = JSONToSkin(mngr, path + '/' + category + '/' + directory);

                    if (skinMap.get(category) != null) {
                        skinMap.get(category).add(skin);
                    } else {
                        // Crea una nueva lista, agrega el skin y ponla en el mapa
                        ArrayList<Skin> skinList = new ArrayList<>();
                        skinList.add(skin);
                        skinMap.put(category, skinList);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Skin> getBackgrounds() {
        return skinMap.get("backgrounds");
    }

}
