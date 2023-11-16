package com.practica1.desktopengine;


import com.practica1.graphics.ColorJ;
import com.practica1.graphics.Graphics;
import com.practica1.graphics.IFont;
import com.practica1.graphics.IImage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DesktopGraphics extends Graphics {
    private JFrame myView;
    private Graphics2D graphics2D;
    private DesktopFont actualDFont;
    private DesktopImage actualDImage;
    private DesktopColor actualColor;
    private BufferStrategy bufferStrategy;
    private int relWidht;
    private int relHeight;
    private String imagesRoute = "assets/";
    private String fontsRoute = "assets/";

    private AffineTransform original;
    private AffineTransform changed;

    private Map<String, DesktopImage> imageMap = new HashMap<>();


    //Obtenemos el buffer strategy y el graphics2D, ademas de la variables usadas por el sistema
    public DesktopGraphics(JFrame myView, int rW, int rH) {
        super();
        this.myView = myView;
        this.bufferStrategy = this.myView.getBufferStrategy();
        graphics2D = (Graphics2D) this.bufferStrategy.getDrawGraphics();
        this.graphics2D = (Graphics2D) this.bufferStrategy.getDrawGraphics();

        relHeight = rH;
        relWidht = rW;

        actualDFont = new DesktopFont("", 50, false);
        actualDImage = new DesktopImage();
        actualColor = new DesktopColor(255, 255, 255);

        original = graphics2D.getTransform();
    }

    @Override
    public void RenderCircle(int x, int y, int r, ColorJ c) {
        actualColor.set(c.getR(), c.getG(), c.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        this.graphics2D.fillOval((int) x, (int) y, (int) r * 2, (int) r * 2);
        this.graphics2D.setPaintMode();
    }

    @Override
    public void RenderRect(int x, int y, int w, int h, ColorJ c) {
        actualColor.set(c.getR(), c.getG(), c.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        this.graphics2D.drawRect(x, y, w, h);
    }

    @Override
    public void RenderFillRect(int x, int y, int w, int h, ColorJ c, ColorJ insideC) {
        actualColor.set(c.getR(), c.getG(), c.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        this.graphics2D.fillRect(x, y, w, h);
        RenderRect(x, y, w, h, c);
    }

    @Override
    public void RenderImage(IImage image, int x, int y, int w, int h) {
        actualDImage = ((DesktopImage) image);
        this.graphics2D.drawImage(actualDImage.getImage(), x, y, w, h, null);
    }

    @Override
    public IImage createImage(String route) {
        actualDImage = imageMap.get(route);

        if (actualDImage == null) {

            actualDImage = new DesktopImage();
            try {
                actualDImage.setImage(ImageIO.read(new File(this.imagesRoute + route)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Almacena la imagen en el mapa con su ruta como clave
            imageMap.put(route, actualDImage);
        }

        return actualDImage;
    }

    @Override
    public void RenderLine(int x1, int y1, int x2, int y2, ColorJ c) {
        actualColor.set(c.getR(), c.getG(), c.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        this.graphics2D.drawLine(x1, y1, x2, y2);
    }

    @Override
    public void RenderText(int x, int y, int size, String txt, ColorJ c) {
        actualColor.set(c.getR(), c.getG(), c.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        int yDown = y + graphics2D.getFontMetrics().getHeight() / 2;
        this.graphics2D.drawString(txt, x, yDown);
    }

    @Override
    public void SetFont(IFont font) {
        actualDFont = ((DesktopFont) font);
        this.graphics2D.setFont(actualDFont.getFont());
    }

    @Override
    public DesktopFont CreateFont(String route, int size, boolean boldStyle) {
        actualDFont.createFont(fontsRoute + route, size, boldStyle);
        this.graphics2D.setFont(actualDFont.getFont());
        return actualDFont;
    }

    @Override
    public DesktopFont GetFont(String name) {
        return actualDFont;
    }

    @Override
    public int GetHeight() {
        return myView.getHeight();
    }

    @Override
    public int GetWidth() {
        return myView.getWidth();
    }

    @Override
    public int GetHeightRelative() {
        return relHeight;
    }

    @Override
    public int GetWidthRelative() {
        return relWidht;
    }

    @Override
    public void save() {
        changed = graphics2D.getTransform();
        graphics2D.setTransform(original);
    }

    @Override
    public void restore() {
        graphics2D.setTransform(changed);
    }

    @Override
    public void CleanScreen(ColorJ color) {
        actualColor.set(color.getR(), color.getG(), color.getB());
        this.graphics2D.setColor(actualColor.getActualColor());
        this.graphics2D.fillRect(0, 0, myView.getWidth(), myView.getHeight());
    }

    @Override
    public boolean startRender() {
        graphics2D = (Graphics2D) this.bufferStrategy.getDrawGraphics();
        return graphics2D != null;
    }

    @Override
    public void finishRender() {
        graphics2D.dispose();
        this.bufferStrategy.show();
    }

    @Override
    public void doTranslate(float tx, float ty) {
        graphics2D.translate(tx, ty);
    }

    @Override
    public void doScale(float scale) {
        graphics2D.scale(scale, scale);
    }
}
