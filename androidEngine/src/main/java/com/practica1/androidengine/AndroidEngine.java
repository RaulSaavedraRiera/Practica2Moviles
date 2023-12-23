package com.practica1.androidengine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.mobileManagers.AdsFinishCallback;
import com.practica1.androidengine.mobileManagers.Mobile;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Extension of the `Engine` class and is responsible for managing
 * the core functionality of a game engine in an Android application.
 */
public class AndroidEngine implements Runnable {

    protected AndroidGraphics graphics = null;
    protected IScene activeIScene = null, sceneToChange = null;
    protected AndroidAudio audioSystem = null;
    boolean changeScene = false;
    protected InputAndroid input = null;
    ColorJ fColor = new ColorJ(255, 255, 255);
    private Thread renderThread;
    private volatile boolean running = false;
    private SurfaceHolder holder;
    private SurfaceView myView;
    private NDKManager ndkManager = new NDKManager();
    private Mobile mobile;

    public AndroidEngine(SurfaceView surfaceView) {
        super();
        myView = surfaceView;
        this.holder = this.myView.getHolder();
        graphics = new AndroidGraphics(new Canvas(), holder, myView, 600, 1000, myView.getContext().getAssets());
        input = new InputAndroid(myView);
        audioSystem = new AndroidAudio(myView.getContext().getAssets());
    }

    /**
     * Render principal que se encarga de llamar al render de la escena actual
     */
    protected void Render() {
        graphics.CleanScreen(fColor);
        activeIScene.renderScene(graphics);
    }

    public Context getContext() {
        return myView.getContext().getApplicationContext();
    }

    /**
     * Update principal que se encarga de llamar al update de la escena actual
     */
    protected void Update(float deltaTime) {
        activeIScene.updateScene(this, deltaTime);
    }


    /**
     * Método para el bucle principal de los juegos
     */
    @Override
    public void run() {

        if (renderThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        while (this.running && graphics.GetWidth() == 0) ;

        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;
        // Seteamos escena inicial
        manageChangeScene();

        // Bucle de juego principal.
        while (running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Informe de FPS
            float elapsedTime = (float) (nanoElapsedTime / 1.0E9);
            this.Update(elapsedTime);
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                //System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            if (graphics.startRender()) {

                graphics.save();
                graphics.rescale();

                Render();
                graphics.restore();

                graphics.finishRender();
                handleInput();
            }

            // Comprobamos si se tiene que realizar un cambio de escena
            manageChangeScene();
        }
    }


    /**
     * HandleInput transforma las coordenadas del input para tener en cuenta
     * el escalado y el desplazamiento que haya
     */
    protected void handleInput() {
        ArrayList<TouchEvent> touchEvents = input.getTouchEvent();

        for (TouchEvent e : touchEvents) {

            //Coordenadas actuales del input
            e.setX(e.getX() - this.graphics.getTranslateX());
            e.setY(e.getY() - this.graphics.getTranslateY());

            e.setX(e.getX() / this.graphics.getScale());
            e.setY(e.getY() / this.graphics.getScale());
        }
        this.activeIScene.handleInput(touchEvents);
    }


    /**
     * Creación del hilo principal
     */

    public void resume() {
        if (!this.running) {
            // Solo hacemos algo si no nos estábamos ejecutando ya
            // (programación defensiva)
            this.running = true;
            // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
            this.renderThread = new Thread(this);
            this.renderThread.start();
        }
    }

    /**
     * Pausar el hilo
     */
    public void pause() {
        if (this.running) {
            this.running = false;
            while (true) {
                try {
                    this.renderThread.join();
                    this.renderThread = null;
                    break;
                } catch (InterruptedException ie) {
                    // Esto no debería ocurrir nunca...
                }
            }
        }
    }

    /**
     * Guardamos la escena a la que queremos cambiar y decimos que hay que cambiar de escena
     *
     * @param IScene
     */

    public void setScene(IScene IScene) {
        sceneToChange = IScene;
        changeScene = true;
    }

    /**
     * Si tenemos que cambiar de escena actualizamos los parametros
     */
    void manageChangeScene() {
        if (!changeScene)
            return;

        activeIScene = sceneToChange;
        sceneToChange = null;
        changeScene = false;
    }


    public AndroidGraphics getGraphics() {
        return graphics;
    }


    public AndroidAudio getAudioSystem() {
        return audioSystem;
    }

    public void generateMobile(AppCompatActivity app, Activity act) {
        if (app == null || mobile != null)
            return;

        mobile = new Mobile(app, myView, act);
    }

    public void generateBanner(int adViewID) {
        mobile.GenerateBanner(adViewID);
    }

    public void SolicitateNotification(int icon, String title, String body, String channelName, int time, TimeUnit timeUnit) {
        mobile.solicitateNotification(icon, title, body, channelName, time, timeUnit);
    }

    public void solicitateShare(Bitmap bitmap, String mnsg) {
        mobile.solicitateShare(bitmap, mnsg);
    }

    public void destroyNotification() {
        mobile.destroyNotificationWorker();
    }

    public void solicitateLoadRewardAd() {
        mobile.loadRewardedAd();
    }

    public void solicitateRewardAd(AdsFinishCallback adFinish) {
        mobile.showRewardedAd(adFinish);
    }

    public SensorManager getSensorManager() {
        return mobile.getSensorManager();
    }

    public Sensor getAccelerometer() {
        return mobile.getAccelerometer();
    }

    public String doGenerateHash(String file) {
        return ndkManager.generateHash(file);
    }

    public void loadLibraries() {
        System.loadLibrary("androidEngine");
    }
}
