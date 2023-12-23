package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.PixelCopy;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.practica1.androidengine.mobileManagers.ScreenShootFinish;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AndroidGraphics {
    private final SurfaceView myView;
    private final SurfaceHolder holder;
    private final AssetManager assets;
    private Paint paint;
    private AndroidFont actualDFont;
    private AndroidImage actualDImage;
    private Canvas canvas;
    private int relWidth, relHeight, tX, tY;
    private Map<String, AndroidImage> imageMap = new HashMap<>();
    float scl;

    public void setScl(float scl) {
        this.scl = scl;
    }

    public void settX(int tX) {
        this.tX = tX;
    }

    public void settY(int tY) {
        this.tY = tY;
    }

    /**
     * Constructor for the AndroidGraphics class.
     *
     * @param canvas The canvas used for drawing.
     * @param holder The surface holder of the canvas.
     * @param myView The SurfaceView associated with the canvas.
     * @param rW     Relative width of the canvas.
     * @param rH     Relative height of the canvas.
     * @param assets The AssetManager for accessing assets.
     */
    public AndroidGraphics(Canvas canvas, SurfaceHolder holder, SurfaceView myView, int rW, int rH, AssetManager assets) {
        this.canvas = canvas;
        this.holder = holder;
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
        this.myView = myView;
        this.relHeight = rH;
        this.relWidth = rW;
        this.actualDFont = new AndroidFont("Arial", 50, false);
        this.assets = assets;
    }

    public AssetManager getAssets() {
        return assets;
    }

    public int getTranslateY() {
        return tY;
    }

    public int getTranslateX() {
        return tX;
    }

    public float getScale() {
        return scl;
    }

    /**
     * Draws a filled circle with the specified center coordinates (x, y), radius (r), and outline color (c)
     *
     * @param x: x-coordinate of the center of the circle.
     * @param y: y-coordinate of the center of the circle.
     * @param r: radius of the circle.
     * @param c: color of the circle.
     */
    public void renderCircle(int x, int y, int r, ColorJ c) {
        paint = new Paint();
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB()));
        canvas.drawCircle(x + r, y + r, r, paint);
    }

    /**
     * Draws an empty rectangle with the specified position, dimensions, and outline color.
     *
     * @param x: x-coordinate of the top-left corner of the rectangle.
     * @param y: y-coordinate of the top-left corner of the rectangle.
     * @param w: width of the rectangle.
     * @param h: height of the rectangle.
     * @param c: color of the outline.
     */

    public void renderRect(int x, int y, int w, int h, ColorJ c) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE); // Establece el estilo del contorno
        paint.setStrokeWidth(2); // Establece el grosor del contorno
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB())); // Establece el color del contorno
        canvas.drawRect(x, y, x + w, y + h, paint);
    }

    /**
     * Draws a filled rectangle with the given position, dimensions, outline color, and fill color,
     *
     * @param x:       x-coordinate of the top-left corner of the rectangle.
     * @param y:       y-coordinate of the top-left corner of the rectangle.
     * @param w:       width of the rectangle.
     * @param h:       height of the rectangle.
     * @param c:       color parameter, for the outline color of the rectangle.
     * @param insideC: color parameter, for the fill color of the rectangle.
     */

    public void renderFillRect(int x, int y, int w, int h, ColorJ c, ColorJ insideC) {
        paint = new Paint();
        paint.setColor(Color.argb(c.getA(), c.getR(), c.getG(), c.getB()));
        canvas.drawRect(x, y, x + w, y + h, paint);
        renderRect(x, y, w, h, insideC);
    }

    public void renderFillRect(int x, int y, int w, int h, ColorJ c, ColorJ insideC, int cornerRadius) {
        paint = new Paint();
        paint.setColor(Color.argb(c.getA(), c.getR(), c.getG(), c.getB()));
        canvas.drawRoundRect(new RectF(x, y, x + w, y + h), cornerRadius, cornerRadius, paint);
    }

    /**
     * draws an image on a canvas at the specified position (x, y) with a specified width and height (w, h).
     *
     * @param image: The image to be rendered.
     * @param x:     X-coordinate on the canvas.
     * @param y:     Y-coordinate on the canvas.
     * @param w:     Width on the canvas.
     * @param h:     Height on the canvas.
     */

    public void renderImage(AndroidImage image, int x, int y, int w, int h) {
        AndroidImage androidImage = image;
        Rect src = new Rect();
        src.set(0, 0, androidImage.getWidth(), androidImage.getHeight());
        Rect dst = new Rect();
        dst.set(x, y, x + w, y + h);
        Paint paint = new Paint();
        this.canvas.drawBitmap(androidImage.getImage(), src, dst, paint);
    }

    /**
     * Draws a line from (x1, y1) to (x2, y2) with the specified color (c).
     *
     * @param x1: x-coordinate of the starting point.
     * @param y1: y-coordinate of the starting point.
     * @param x2: x-coordinate of the ending point.
     * @param y2: y-coordinate of the ending point.
     * @param c:  color of the line.
     */

    public void renderLine(int x1, int y1, int x2, int y2, ColorJ c) {
        paint.setColor(Color.argb(100, c.getR(), c.getG(), c.getB()));
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    /**
     * Renders text at the specified position (x, y) with a specific font size, text content, and color.
     *
     * @param x:    x-coordinate of the text position.
     * @param y:    y-coordinate of the text position.
     * @param size: Font size for the text.
     * @param txt:  Text content to be rendered.
     * @param c:    Color of the text.
     */

    public void renderText(int x, int y, int size, String txt, ColorJ c) {
        paint.setTextSize(size);
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB()));
        Paint.FontMetrics fm = paint.getFontMetrics();
        float halfFontHeight = (fm.descent - fm.ascent) / 2;
        float yDown = y + halfFontHeight;
        canvas.drawText(txt, x, yDown, paint);
    }

    /**
     * Sets the current font for rendering text.
     *
     * @param font: The font to be set as the current font.
     */

    public void setFont(AndroidFont font) {
        actualDFont = font;
    }

    /**
     * Creates a font with the specified font file path, size, and style.
     *
     * @param route:     The font file path.
     * @param size:      Font size.
     * @param boldStyle: Style flag for font boldness.
     * @return The created AndroidFont instance.
     */

    public AndroidFont createFont(String route, int size, boolean boldStyle) {
        paint = actualDFont.createFont(route, size, boldStyle, assets);
        return actualDFont;
    }


    /**
     * Gets the current font by name.
     *
     * @param name: Name of the font.
     * @return The current font.
     */

    public AndroidFont getFont(String name) {
        return actualDFont;
    }

    /**
     * Gets the height of the rendering canvas.
     *
     * @return The height of the canvas.
     */

    public int getHeight() {
        return myView.getHeight();
    }

    /**
     * Gets the width of the rendering canvas.
     *
     * @return The width of the canvas.
     */

    public int getWidth() {
        return myView.getWidth();
    }

    /**
     * Gets the relative height of the rendering canvas.
     *
     * @return The relative height of the canvas.
     */

    public int getHeightRelative() {
        return relHeight;
    }

    /**
     * Gets the relative width of the rendering canvas.
     *
     * @return The relative width of the canvas.
     */

    public int getWidthRelative() {
        return relWidth;
    }

    /**
     * Clears the screen with the specified color.
     *
     * @param color: The color used to clear the screen.
     */

    public void cleanScreen(ColorJ color) {
        paint.setColor(Color.argb(255, color.getR(), color.getG(), color.getB()));
        canvas.drawColor(Color.argb(255, color.getR(), color.getG(), color.getB()));
        this.canvas.drawRect(0, 0, myView.getWidth(), myView.getHeight(), paint);
    }


    public void setBackgroundImage(AndroidImage backgroundImage) {
        if (canvas != null) {
            int marginTop = 100;
            Rect src = new Rect();
            src.set(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight());

            int canvasWidth = getWidth();
            int posY = marginTop;

            Rect dst = new Rect();
            dst.set(0, posY, canvasWidth, posY + backgroundImage.getHeight());
            Paint paint = new Paint();
            this.canvas.drawBitmap(backgroundImage.getImage(), src, dst, paint);
        }

    }

    /**
     * Creates an image from the specified file route.
     *
     * @param route: The file route of the image.
     * @return The created AndroidImage instance.
     */
    public AndroidImage createImage(String route) {
        actualDImage = imageMap.get(route);

        if (actualDImage == null) {
            // Si la imagen no está en el mapa, cárgala
            Bitmap bitmap = null;
            try {
                InputStream is = this.assets.open(route);
                bitmap = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Crea una instancia de AndroidImage y almacénala en el mapa
            actualDImage = new AndroidImage();
            actualDImage.setBitmap(bitmap);
            actualDImage.setRoute(route);
            imageMap.put(route, actualDImage);
        }

        return actualDImage;
    }

    /**
     * Initiates the rendering process and locks the canvas for rendering.
     *
     * @return True if the rendering process has started successfully.
     */

    public boolean startRender() {
        while (!this.holder.getSurface().isValid()) ;
        this.canvas = this.holder.lockCanvas();
        return true;
    }

    /**
     * Finishes the rendering process and unlocks the canvas.
     */

    public void finishRender() {
        this.holder.unlockCanvasAndPost(canvas);
    }

    /**
     * Saves the current state of the canvas, allowing for later restoration.
     */

    public void save() {
        canvas.save();
    }

    /**
     * Restores the previously saved state of the canvas.
     */
    public void restore() {
        canvas.restore();
    }

    public void rescale() {
        float w = getWidth();
        float h = getHeight();
        float scale = Math.min(w / getWidthRelative(), h / getHeightRelative());

        float transformX = (getWidth() - (getWidthRelative() * scale)) / 2;
        float transformY = (getHeight() - (getHeightRelative() * scale)) / 2;

        if (transformX > 0) {
            transformX = (getWidth() / 2) - getWidthRelative() * scale / 2;
        } else {
            transformX = 0;
        }

        if (transformY > 0) {
            transformY = (getHeight() / 2) - getHeightRelative() * scale / 2;
        } else {
            transformY = 0;
        }

        setScl(scale);
        settX((int) transformX);
        settY((int) transformY);

        canvas.translate(transformX, transformY);
        canvas.scale(scale, scale);
    }

    public void generateScreenShoot(int x, int y, int w, int h, ScreenShootFinish callBack) {
        Bitmap bitmap = Bitmap.createBitmap(myView.getWidth(), myView.getHeight(),
                Bitmap.Config.ARGB_8888);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HandlerThread handlerThread = new HandlerThread("PixelCopier");
            handlerThread.start();
            PixelCopy.request(myView, bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                @Override
                public void onPixelCopyFinished(int copyResult) {
                    if (copyResult == PixelCopy.SUCCESS) {
                        // generar Bitmap a partir de otro dadas unas coordenadas y un tamaño
                        Bitmap finalBitmap = Bitmap.createBitmap(bitmap, x, y, w, h);
                        //shareImage(finalBitmap, "Me he pasado el Mastermind!!");

                        callBack.doAction(finalBitmap);

                    }
                    handlerThread.quitSafely();

                }
            }, new Handler(handlerThread.getLooper()));
        }
    }
}
