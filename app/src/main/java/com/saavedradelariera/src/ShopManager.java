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
    final String path = "store";
    int currentPage = 0;
    int balance = 100;
    private ArrayList<String> categories = new ArrayList<String>();
    private Map<String, Map<String, Skin>> skinMap = new HashMap<>();
    private Map<String, String> activeSkinsMap = new HashMap<>();
    private static ShopManager instance = null;
    private ArrayList<String> boughtSkins = new ArrayList<>();

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    public boolean changePage(boolean nextPage) {
        if(nextPage && currentPage+1 < categories.size())
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
        loadShop(engine);
    }

    private void loadShop(AndroidEngine engine) {
        readCategories(engine.getContext());
        readSkins(engine.getContext());
    }

    private void readCategories(Context c)
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

    private Skin JSONToSkin(AssetManager mngr, String filePath, String category) {
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

            Skin skin = new Skin(title, price, samplePath, skinPath, category);
            return skin;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void readSkins(Context c)
    {
        AssetManager mngr = c.getAssets();

        try {
            for(String category : categories)
            {
                String[] files = mngr.list(path+'/'+category);

                for (String file : files) {
                    Skin skin = JSONToSkin(mngr, path + '/' + category + '/' + file, category);

                    if (skinMap.get(category) == null) {
                        skinMap.put(category, new HashMap<>());
                    }
                    skinMap.get(category).put(skin.getTitle(), skin);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Skin> getSkinsByCat(String cat) {
        if (skinMap.get(cat) != null) {
            ArrayList<Skin> skinsList = new ArrayList<>(skinMap.get(cat).values());
            return skinsList;
        } else {
            return new ArrayList<>();
        }
    }

    public String getCategory(int catId) {
        return categories.get(catId);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public void addBoughtSkin(String skinTitle) {
        boughtSkins.add(skinTitle);
    }

    public void setActiveSkin(String category, String skinName) {
        activeSkinsMap.put(category, skinName);
    }
}
