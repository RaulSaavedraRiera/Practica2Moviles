package com.saavedradelariera.src;

/*Clase para mostrar una skin en la tienda. Contiene todos los datos necesarios:
* precio del paquete, ruta de un ejemplo del paquete de skins, así como métodos para adquirirla
* y acceder a su informacion */
public class Skin {
    private boolean bought;
    private int price;
    private String samplePath, skinsPath, title, category;

    public String getCategory() {
        return category;
    }

    //Generamos la skin con datos especificos
    public Skin(String title, int price, String samplePath, String skinsPath, String category) {
        this.title = title;
        this.price = price;
        this.samplePath = samplePath;
        this.skinsPath = skinsPath;
        this.category = category;
    }

    //método para marcar si el paquete de skins ya esta adquirida o no
    public void setBought(boolean bought) {
        this.bought = bought;
    }

    //Devuelve la ruta del ejemplo
    public String getSamplePath() {
        return samplePath;
    }

    public String getTitle() {
        return title;
    }

    //Devuelve si ha sido comprada o no
    public boolean getBought() {
        return bought;
    }

    //Devuelve el rpecio
    public int getPrice() {
        return price;
    }

    //Devuelve la ruta del paquete de skins
    public String getSkinsPath() {
        return skinsPath;
    }
}
