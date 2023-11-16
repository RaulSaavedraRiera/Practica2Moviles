package com.practica1.engine;

import com.practica1.graphics.ColorJ;
import com.practica1.graphics.IGraphics;
import com.practica1.input.IInput;
import com.practica1.input.TouchEvent;
import com.practica1.sound.IAudioSystem;

import java.util.ArrayList;

/**
 * Clase base para el funcionamiento general de la estructura del juego.
 * En esta se implementarán los métodos base para los diferentes módulos
 */
public abstract class Engine implements Runnable, IEngine {
    protected IGraphics graphics = null;
    protected IScene activeIScene = null, sceneToChange = null;
    protected IAudioSystem audioSystem = null;
    boolean changeScene = false;
    protected IInput input = null;
    ColorJ fColor = new ColorJ(255, 255, 255);
    ColorJ sColor = new ColorJ(80, 80, 80);
    private Thread renderThread;
    private boolean running = false;


    public Engine() {
    }

    /**
     * Render principal que se encarga de llamar al render de la escena actual
     *
     * */
    protected void Render() {
        activeIScene.RenderScene(graphics);
    }

    /**
     * Update principal que se encarga de llamar al update de la escena actual
     *
     * */
    protected void Update(float deltaTime) {
        activeIScene.UpdateScene(this, deltaTime);
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
        ManageChangeScene();

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
                graphics.CleanScreen(fColor);
                graphics.rescale();

                Render();
                graphics.restore();

                graphics.finishRender();
                HandleInput();
            }

            // Comprobamos si se tiene que realizar un cambio de escena
            ManageChangeScene();
        }
    }


    /**
     * HandleInput transforma las coordenadas del input para tener en cuenta
     * el escalado y el desplazamiento que haya
     */
    protected void HandleInput() {
        ArrayList<TouchEvent> touchEvents = input.getTouchEvent();

        for (TouchEvent e : touchEvents) {

            //Coordenadas actuales del input
            e.setX(e.getX() - this.graphics.getTranslateX());
            e.setY(e.getY() - this.graphics.getTranslateY());

            e.setX(e.getX() / this.graphics.getScale());
            e.setY(e.getY() / this.graphics.getScale());
        }
        this.activeIScene.HandleInput(touchEvents);
    }


    /**
     * Creación del hilo principal
     */
    @Override
    public void Resume() {
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
    @Override
    public void Pause() {
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
    @Override
    public void SetScene(IScene IScene) {
        sceneToChange = IScene;
        changeScene = true;
    }

    /**
     * Si tenemos que cambiar de escena actualizamos los parametros
     */
    void ManageChangeScene() {
        if (!changeScene)
            return;

        activeIScene = sceneToChange;
        sceneToChange = null;
        changeScene = false;
    }

    @Override
    public IGraphics GetGraphics() {
        return graphics;
    }

    @Override
    public IAudioSystem GetAudioSystem() {return audioSystem;}
}
