package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;

/*
 * Extensión de la clase `FontJ` y representa una fuente para la renderización de texto en una aplicación de Android.
 * Proporciona métodos para acceder a las propiedades de la fuente, establecer el tamaño de la fuente, el estilo en negrita,
 * y crear un objeto Paint personalizado para renderizar texto.
 */
public class AndroidFont {
    private Typeface actualFont;
    private Paint paint;

    protected String Route;
    protected int Size;
    protected boolean Bold;
    Map<String, Typeface> fontMap = new HashMap<>();

    public AndroidFont(String fontName, int size, boolean bold) {
        Route = fontName;
        Bold = bold;
        Size = size;
        paint = new Paint();
    }

    @RequiresApi(api = 34)
    public String getName() {
        return actualFont.getSystemFontFamilyName();
    }

    public boolean getBold() {
        return Bold;
    }

    public int getSize() {
        return (int) paint.getTextSize();
    }

    public void setSize(int size) {
        paint.setTextSize(size);
    }

    public void setBold(boolean to) {
        int style = this.Bold ? Typeface.BOLD : Typeface.NORMAL;
        Typeface newFont = Typeface.create(actualFont, style);
        actualFont = newFont;
    }

    /*
     * Genera un objeto Paint personalizado con el estilo, tamaño y ruta de recurso especificados, devolviéndolo para la renderización de texto.
     */
    public Paint createFont(String route, int size, boolean boldStyle, AssetManager assetManager) {

        paint = new Paint();

        actualFont = fontMap.get(route);

        if (actualFont == null) {
            int style = boldStyle ? Typeface.BOLD : Typeface.NORMAL;
            Typeface customTypeface = Typeface.createFromAsset(assetManager, route);
            customTypeface = Typeface.create(customTypeface, style);
            actualFont = customTypeface;
            fontMap.put(route, actualFont);
        }

        paint.setTextSize(size);
        paint.setTypeface(actualFont);
        return paint;
    }

    public Typeface getFont() {
        return actualFont;
    }
    public String getRoute() {
        return Route;
    }
}
