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

    /*
     * Constructor para la clase AndroidGraphics.
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

    /*
     * Dibuja un círculo relleno con las coordenadas de centro especificadas (x, y), radio (r) y color de contorno (c).
     */
    public void renderCircle(int x, int y, int r, ColorJ c) {
        paint = new Paint();
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB()));
        canvas.drawCircle(x + r, y + r, r, paint);
    }

    /*
     * Dibuja un rectángulo vacío con la posición, dimensiones y color de contorno especificados.
     */
    public void renderRect(int x, int y, int w, int h, ColorJ c) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE); // Establece el estilo del contorno
        paint.setStrokeWidth(2); // Establece el grosor del contorno
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB())); // Establece el color del contorno
        canvas.drawRect(x, y, x + w, y + h, paint);
    }

    /*
     * Dibuja un rectángulo relleno con la posición, dimensiones, color de contorno y color de relleno especificados.
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

    /*
     * draws an image on a canvas at the specified position (x, y) with a specified width and height (w, h).
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

    /*
     * Dibuja una línea desde (x1, y1) hasta (x2, y2) con el color especificado (c).
     */
    public void renderLine(int x1, int y1, int x2, int y2, ColorJ c) {
        paint.setColor(Color.argb(100, c.getR(), c.getG(), c.getB()));
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    /*
     * Renderiza texto en la posición especificada (x, y) con un tamaño de fuente específico, contenido de texto y color.
     */

    public void renderText(int x, int y, int size, String txt, ColorJ c) {
        paint.setTextSize(size);
        paint.setColor(Color.argb(255, c.getR(), c.getG(), c.getB()));
        Paint.FontMetrics fm = paint.getFontMetrics();
        float halfFontHeight = (fm.descent - fm.ascent) / 2;
        float yDown = y + halfFontHeight;
        canvas.drawText(txt, x, yDown, paint);
    }

    /*
     * Establece la fuente actual para renderizar texto.
     */

    public void setFont(AndroidFont font) {
        actualDFont = font;
    }

    /*
     * Crea una fuente con la ruta de archivo, tamaño y estilo especificados.
     */
    public AndroidFont createFont(String route, int size, boolean boldStyle) {
        paint = actualDFont.createFont(route, size, boldStyle, assets);
        return actualDFont;
    }


    /*
     * Obtiene la fuente actual por nombre.
     */

    public AndroidFont getFont(String name) {
        return actualDFont;
    }

    public int getHeight() {
        return myView.getHeight();
    }

    public int getWidth() {
        return myView.getWidth();
    }

    public int getHeightRelative() {
        return relHeight;
    }

    public int getWidthRelative() {
        return relWidth;
    }

    /*
     * Limpia la pantalla con el color especificado.
     */

    public void cleanScreen(ColorJ color) {
        paint.setColor(Color.argb(255, color.getR(), color.getG(), color.getB()));
        canvas.drawColor(Color.argb(255, color.getR(), color.getG(), color.getB()));
        this.canvas.drawRect(0, 0, myView.getWidth(), myView.getHeight(), paint);
    }


    /*
     * Establece la imagen de fondo del lienzo.
     */
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

    /*
     * Crea una imagen a partir de la ruta de archivo especificada.
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

    /*
     * Inicia el proceso de renderizado y bloquea el lienzo para renderizar.
     */

    public boolean startRender() {
        while (!this.holder.getSurface().isValid()) ;
        this.canvas = this.holder.lockCanvas();
        return true;
    }

    /*
     * Finaliza el proceso de renderizado y desbloquea el lienzo.
     */
    public void finishRender() {
        this.holder.unlockCanvasAndPost(canvas);
    }

    /*
     * Guarda el estado actual del lienzo, permitiendo su restauración posterior.
     */
    public void save() {
        canvas.save();
    }

    /*
     * Restaura el estado previamente guardado del lienzo.
     */
    public void restore() {
        canvas.restore();
    }

    /*
     * Reescala la vista según las proporciones relativas especificadas.
     */
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

    /*
     * Genera una captura de pantalla de la región especificada dentro de la vista y realiza una acción de devolución de llamada.
     */
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
