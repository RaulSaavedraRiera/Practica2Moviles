package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;

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

    int currentPage = 0;
    private ArrayList<String> categories = new ArrayList<String>();

    public Map<String, ArrayList<Skin>> skinMap = new HashMap<>();
    public Map<String, ArrayList<Skin>> iconsMap = new HashMap<>();

    //public Map<String, ArrayList<Skin>>  = new HashMap<>();
    private static ShopManager instance = null;

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    public boolean changePage(boolean nextPage) {
        if(nextPage)
        {
            currentPage++;
            return true;
        }else if (!nextPage && currentPage - 1 >= 0){
            currentPage--;
            return true;
        }
        return false;
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

            for (String cat : directories) {
                categories.add(cat);
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
            String samplePath = jsonObject.getString("samplePath");
            String skinPath = jsonObject.getString("skinsPath");
            int price = jsonObject.getInt("price");

            Skin skin = new Skin(title, price, samplePath, skinPath);
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
            for(String category : categories)
            {
                String[] files = mngr.list(path+'/'+category);

                for (String file : files) {
                    Skin skin = JSONToSkin(mngr, path + '/' + category + '/' + file);

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

    public ArrayList<Skin> getSkinsByCat(String cat) { return skinMap.get(cat);}

    public ArrayList<Skin> getSkinsByCatId(int catId) { return skinMap.get(categories.get(catId));}

    public String getCategory(int catId) {
        return categories.get(catId);
    }


    public void nextPage() {
        currentPage += 1;
    }

    public void previousPage() {
        currentPage -= 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
