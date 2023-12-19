package com.saavedradelariera.src;

import android.content.Context;
import android.content.res.AssetManager;

import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.ColorJ;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopManager {
    private static final String PATH = "store";
    private int currentPage = 0;
    private int balance = 500;
    private final List<String> categories = new ArrayList<>();
    private final Map<String, Map<String, Skin>> skinMap = new HashMap<>();
    private final Map<String, Skin> activeSkinsMap = new HashMap<>();
    private static ShopManager instance = null;
    private final List<String> boughtSkins = new ArrayList<>();

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    public boolean changePage(boolean nextPage) {
        int size = categories.size();
        if (nextPage && currentPage + 1 < size) {
            currentPage++;
            return true;
        } else if (!nextPage && currentPage - 1 >= 0) {
            currentPage--;
            return true;
        }
        return false;
    }

    public void resetScene() {
        currentPage = 0;
    }

    public void init(AndroidEngine engine) {
        loadShop(engine);
    }

    private void loadShop(AndroidEngine engine) {
        readCategories(engine.getContext());
        readSkins(engine.getContext());
    }

    private void readCategories(Context context) {
        AssetManager assetManager = context.getAssets();

        try {
            String[] directories = assetManager.list(PATH);
            for (String category : directories) {
                categories.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Skin jsonToSkin(AssetManager assetManager, String filePath, String category) {
        try {
            InputStream inputStream = assetManager.open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            String json = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);

            String title = jsonObject.getString("title");
            String skinPath = jsonObject.getString("skinsPath");
            String samplePath = jsonObject.optString("samplePath", null);
            int price = jsonObject.getInt("price");

            if (category.equals("colores")) {
                String primaryColor = jsonObject.getString("primaryColor");
                String secondaryColor = jsonObject.getString("secondaryColor");
                return new ColorSkin(title, price, samplePath, skinPath, category, primaryColor, secondaryColor);
            } else {
                return new Skin(title, price, samplePath, skinPath, category);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void readSkins(Context context) {
        AssetManager assetManager = context.getAssets();

        try {
            for (String category : categories) {
                String[] files = assetManager.list(PATH + '/' + category);

                for (String file : files) {
                    Skin skin = jsonToSkin(assetManager, PATH + '/' + category + '/' + file, category);

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

    public List<Skin> getSkinsByCat(String cat) {
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

    public void setActiveSkin(String category, Skin skin) {
        activeSkinsMap.put(category, skin);
    }

    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    public Skin getActiveSkin(String category) {
        return activeSkinsMap.get(category);
    }

    public void removeActiveSkin(String category) {
        activeSkinsMap.remove(category);
    }

    public ColorJ getBackgroundColor() {
        ColorSkin colorSkin = (ColorSkin) getActiveSkin("colores");
        return (colorSkin != null) ? new ColorJ(colorSkin.getPrimaryColor()) : new ColorJ("#ffffff");
    }

    public ColorJ getButtonsColor() {
        ColorSkin colorSkin = (ColorSkin) getActiveSkin("colores");
        return (colorSkin != null) ? new ColorJ(colorSkin.getSecondaryColor()) : new ColorJ("#00d2b4");
    }
}
