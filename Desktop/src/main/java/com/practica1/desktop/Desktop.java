package com.practica1.desktop;

import com.practica1.desktopengine.DesktopEngine;
import com.practica1.src.SceneManager;
import com.practica1.src.scenes.MenuScene;

import javax.swing.JFrame;

public class Desktop {

    public static void main(String[] args) {
        JFrame mJFrame = new JFrame("APPDESKTOP");
        mJFrame.setSize(480, 800);
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mJFrame.setIgnoreRepaint(true);
        mJFrame.setVisible(true);
        //Iniciamos el buffer 100 veces por si no se realiza correctamente
        int i = 0;
        while (i++ < 100) {
            try {
                mJFrame.createBufferStrategy(2);
            } catch (Exception e) {
            }
        }

        DesktopEngine dkEngine = new DesktopEngine(mJFrame, 600, 1000);
        SceneManager.getInstance().Init(dkEngine);
        MenuScene mS = new MenuScene();
        //Seamos esta escena
        SceneManager.getInstance().SetScene(mS);
        //Lanzamos el hilo de renderizado
        dkEngine.Resume();
    }
}