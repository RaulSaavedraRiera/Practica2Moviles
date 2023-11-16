package com.practica1.desktopengine;


import com.practica1.graphics.FontJ;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase encarga para la utilizacion de diferentes fuentes en Desktop
 */
public class DesktopFont extends FontJ {

    private Font actualFont;
    Map<String, Font> fontMap = new HashMap<>();

    DesktopFont(String route, int size, boolean bold) {
        super(route, size, bold);
    }

    @Override
    public String getName() {
        return actualFont.getFontName();
    }

    @Override
    public int getSize() {
        return actualFont.getSize();
    }

    @Override
    public boolean getBold() {
        return actualFont.isBold();
    }

    @Override
    public void setSize(int size) {
        Font newFont = actualFont.deriveFont((float) size);
        actualFont = newFont;
    }

    @Override
    public void setBold(boolean to) {
        int style = this.Bold ? Font.BOLD : Font.PLAIN;
        Font newFont = actualFont.deriveFont(style);
        actualFont = newFont;
    }

    public void createFont(String route, int size, boolean boldStyle) {

        actualFont = fontMap.get(route);

        if(actualFont == null)
        {
            int style = boldStyle ? Font.BOLD : Font.PLAIN;
            try {
                InputStream is = new FileInputStream(route);
                actualFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(style, size);
            } catch (FontFormatException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            fontMap.put(route, actualFont);
        }else{
            setSize(size);
            setBold(boldStyle);
        }

    }

    public Font getFont() {
        return actualFont;
    }
}
