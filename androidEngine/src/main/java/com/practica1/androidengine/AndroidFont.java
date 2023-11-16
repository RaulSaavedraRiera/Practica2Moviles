package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.annotation.RequiresApi;
import com.practica1.graphics.FontJ;
import java.util.HashMap;
import java.util.Map;

/**
 * Extension of the `FontJ` class and represents a font for text rendering in an Android application.
 * It provides methods to access font properties, set font size, bold style, and create a custom Paint object for rendering text.
 */
public class AndroidFont {
    private Typeface actualFont;
    private Paint paint;

    protected String Route;
    protected int Size;
    protected boolean Bold;
    Map<String, Typeface> fontMap = new HashMap<>();

    AndroidFont(String fontName, int size, boolean bold) {
        Route = fontName;
        Bold = bold;
        Size = size;
        paint = new Paint();
    }
    @RequiresApi(api = 34)
    public String getName() {
        return actualFont.getSystemFontFamilyName();
    }

    /**
     * @return Is actualFont bold or not.
     */

    public boolean getBold() {
        return actualFont.isBold();
    }

    /**
     * @return current textSize
     */

    public int getSize() {
        return (int)paint.getTextSize();
    }

    /**
     * Sets the size of the text.
     * @param size desired size
     */

    public void setSize(int size) {
        paint.setTextSize(size);
    }

    /**
     * Specify if bold text or not
     * @param to boolean
     */

    public void setBold(boolean to) {
        int style = this.Bold ? Typeface.BOLD : Typeface.NORMAL;
        Typeface newFont = Typeface.create(actualFont, style);
        actualFont = newFont;
    }

    /**
     *  Generates a custom Paint object with specified font style, size, and asset route, returning it for text rendering.
     * @param route font route
     * @param size text size
     * @param boldStyle bold style
     * @param assetManager Android assets
     * @return Created paint.
     */
    public Paint createFont(String route, int size, boolean boldStyle, AssetManager assetManager) {

        paint = new Paint();

        actualFont = fontMap.get(route);

        if(actualFont == null)
        {
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

    /**
     * @return current Font
     */
    public Typeface getFont() {
        return actualFont;
    }
}
