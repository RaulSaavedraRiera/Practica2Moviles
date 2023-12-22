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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*Clase que gestiona la tienda y todas las funciones y características que se esperan de ella.
 Se dividen las diferentes skins por páginas, siendo cada una de una categoría (de vbvbolas, de fondo, etc). A su vez
 nos da todos los métodos que necesitamos para el funcionamiento correcto de la tienda:
 comprar skins, fondos, equiparlos, mostrar el dinero actual y usarlo, etc.*/
public class ShopManager {

    private static final String PATH = "store";
    private int currentPage = 0;
    private int balance = 500;
    private final List<String> categories = new ArrayList<>();
    private final Map<String, Map<String, Skin>> skinMap = new HashMap<>();
    private Map<String, Skin> activeSkinsMap = new HashMap<>();
    private Map<String, Set<String>> boughtSkinsMap = new HashMap<>();
    private static ShopManager instance = null;

    public static ShopManager getInstance() {
        if (instance == null) {
            instance = new ShopManager();
        }
        return instance;
    }

    //Devuelve si el cambio de página, sea adelante o atrás, es válido y cambia el valor de esta
    public boolean changePage(boolean nextPage) {
        int size = categories.size();
        //si la operacion es valida se
        if (nextPage && currentPage + 1 < size) {
            currentPage++;
            return true;
        } else if (!nextPage && currentPage - 1 >= 0) {
            currentPage--;
            return true;
        }
        return false;
    }

    //resetea la vairable de paginas de la tienda al estado inicial
    public void resetScene() {
        currentPage = 0;
    }

    //inicializacion en dos pasos
    public void init(AndroidEngine engine) {
        //carga la tienda
        loadShop(engine.getContext());
        //si se obtienen los codigos e skins compradas
        if (boughtSkinsMap.get("codigos") != null) {
            //se van marcando como adquiridas para no poder volver a adquirirlos
            //y mantener la progresion
            for (String skinTitle : boughtSkinsMap.get("codigos")) {
                skinMap.get("codigos").get(skinTitle).setBought(true);
            }
        }
    }

    //Carga la tienda
    private void loadShop(Context context) {
        //lee todas las categorias
        readCategories(context);
        //y las skins
        readSkins(context);
    }

    //Lee las diferentes categorias que encontramos almacenadas en assets
    private void readCategories(Context context) {
        //accede a los assets
        AssetManager assetManager = context.getAssets();
        //y agrega las categorias almacenadas en el directorio de la tienda
        try {
            String[] directories = assetManager.list(PATH);
            for (String category : directories) {
                categories.add(category);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Devuelve la skin generandola mediante la información almacenada en un json
    private Skin jsonToSkin(AssetManager assetManager, String filePath, String category) {
        try {
            //habilita la lectura
            InputStream inputStream = assetManager.open(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            //se lee el json linea a linea
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            //se cierra la lectura
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            //se genera el archivo json
            String json = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);

            //se obtiene la informacion de la skin
            String title = jsonObject.getString("title");
            String skinPath = jsonObject.getString("skinsPath");
            String samplePath = jsonObject.optString("samplePath", null);
            int price = jsonObject.getInt("price");

            //si es de la categoria de colores se accede a atributos especiales de esta y se genera la colorSkin
            if (category.equals("colores")) {
                String primaryColor = jsonObject.getString("primaryColor");
                String secondaryColor = jsonObject.getString("secondaryColor");
                return new ColorSkin(title, price, samplePath, skinPath, category, primaryColor, secondaryColor);
            }
            //si no se genera con los valores normales
            else
            {
                return new Skin(title, price, samplePath, skinPath, category);
            }
            //se recogen excepciones en caso de fallo
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //Se leen la skins. Se necesita el contexto para acceder a los assets de la aplicacion
    private void readSkins(Context context) {
        //se obtiene el directorio de assets
        AssetManager assetManager = context.getAssets();

        try {
            //por cada categoria
            for (String category : categories) {
                String[] files = assetManager.list(PATH + '/' + category);
                //se abren los diferentes archivos
                for (String file : files) {
                    //y se generan sins con ellos
                    Skin skin = jsonToSkin(assetManager, PATH + '/' + category + '/' + file, category);
                    //si es el primero de la categoria se añade dicha categoria
                    if (skinMap.get(category) == null) {
                        skinMap.put(category, new HashMap<>());
                    }
                    //si no se añade a la categoria existente
                    skinMap.get(category).put(skin.getTitle(), skin);
                }
            }
            //se manejan excepciones
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Devuelve una lista de skins asociadas a una cateogira
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

    //Devuelve la cantidad de dinero actual
    public int getBalance() {
        return balance;
    }

    //Modifica la cantidad de dinero actual
    public void addBalance(int amount) {
        balance += amount;
        if(balance < 0)
            balance = 0;
    }

    //Marca la skin como activa dentro de la categoria correspondiente dado la skin y la categoría
    public void setActiveSkin(String category, Skin skin) {
        activeSkinsMap.put(category, skin);
    }

    //Marca la skin como activa dentro de la categoria correspondiente dado el nombre de la skin y la categoría
    public void setActiveSkin(String category, String skinTitle) {
        activeSkinsMap.put(category, getSkin(category, skinTitle));
    }

    //Obtiene todas las categorias
    public List<String> getCategories() {
        return new ArrayList<>(categories);
    }

    //devuelve la skin activa de una categoria
    public Skin getActiveSkin(String category) {
        return activeSkinsMap.get(category);
    }

    //elimina la skin activa de una categoría
    public void removeActiveSkin(String category) {
        activeSkinsMap.remove(category);
    }

    //obtiene el color de fondo. Que encontramos como skin dentro de la categoría colores
    public ColorJ getBackgroundColor() {
        ColorSkin colorSkin = (ColorSkin) getActiveSkin("colores");
        return (colorSkin != null) ? new ColorJ(colorSkin.getPrimaryColor()) : new ColorJ("#ffffff");
    }

    //obtiene el color de los botones. Que encontramos como skin dentro de la categoría colores
    public ColorJ getButtonsColor() {
        ColorSkin colorSkin = (ColorSkin) getActiveSkin("colores");
        return (colorSkin != null) ? new ColorJ(colorSkin.getSecondaryColor()) : new ColorJ("#00d2b4");
    }

    //Nos devuelve una skin dado un titulo y una categoria
    public Skin getSkin(String category, String title) {
        return skinMap.get(category).get(title);
    }

    //Añade una skin a las skins compradas dado su categoria y nombre
    public void addBoughtSkin(String category, String itemName) {
        // si no habia skins compradas de esa categoria crea esa categoria dentro de skins compradas
        if (boughtSkinsMap.get(category) == null) {
            boughtSkinsMap.put(category, new HashSet<>());
        }

        this.boughtSkinsMap.get(category).add(itemName);
    }

    //obtiene todas las skins compradas de una categoria
    public String getBoughtSkinsStr(String category) {
        if (boughtSkinsMap.get(category) != null)
            return boughtSkinsMap.get(category).toString();
        return null;
    }

    //Transforma la cadena de skins compradas de la categoria dada
    //en candenas separadas, una por skin, para que la tienda pueda trabajar con ellas correctamente
    public void loadBoughtSkins(String category, String boughtSkinsStr) {
        boughtSkinsStr = boughtSkinsStr.replace("[", "");
        boughtSkinsStr = boughtSkinsStr.replace("]", "");

        String[] boughtSkinsArray = boughtSkinsStr.split(", ");

        for (int i = 0; i < boughtSkinsArray.length; i++) {
            if (skinMap.get(category).get(boughtSkinsArray[i]) != null) {
                skinMap.get(category).get(boughtSkinsArray[i]).setBought(true);
                addBoughtSkin(category, boughtSkinsArray[i]);
            }
        }
    }

    //iguala el saldo actual al argumento pasado
    public void setBalance(int balance) {
        this.balance = balance;
    }

    //elimina todos los datos reseteando la informacion de la tienda y la recarga
    public void eraseData() {
        boughtSkinsMap = new HashMap<>();
        activeSkinsMap = new HashMap<>();
        balance = 500;
        loadShop(ResourcesManager.getInstance().getCurrentContext());
    }
}