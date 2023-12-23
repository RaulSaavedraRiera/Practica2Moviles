package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.IScene;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.IGameObject;
import com.saavedradelariera.src.AudioManager;
import com.saavedradelariera.src.SceneManager;
import com.saavedradelariera.src.messages.Message;

import java.util.ArrayList;

/*Clase scene abstracta que inicialia la estructura comun*/
public class Scene implements IScene {
    protected String name = "";
    protected ArrayList<IGameObject> GOList = new ArrayList<IGameObject>();
    protected ArrayList<IGameObject> GOMessageList = new ArrayList<>();

    protected boolean inverseRender;

    //inicialzia la escena generando su audiomanager

    public void setScene(AndroidGraphics graphics, AndroidAudio audioSystem) {
        AudioManager aM = new AudioManager(audioSystem);
        addGO(aM);
        SceneManager.getInstance().registerToMessage(aM);
    }

    public String getStateScene() {
        return "NONE";
    }

    public void addGO(IGameObject g) {
        GOList.add(g);
    }

    public void addGOToMessages(IGameObject g) {
        GOMessageList.add(g);
    }

    public void removeGO(IGameObject g) {
        GOList.remove(g);
    }

    public void moveToBottonGO(IGameObject g) {
        GOList.remove(g);
        if (!inverseRender)
            GOList.add(0, g);
        else
            GOList.add(g);
    }

    public void moveToTopGO(IGameObject g) {
        GOList.remove(g);
        if (!inverseRender)
            GOList.add(g);
        else
            GOList.add(0, g);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    //llama al render de todos los GO asociados
    @Override
    public void renderScene(AndroidGraphics iGraphics) {

        if (!inverseRender)
            for (int i = 0; i < GOList.size(); i++) {
                GOList.get(i).render(iGraphics);
            }
        else
            for (int i = GOList.size() - 1; i >= 0; i--) {
                GOList.get(i).render(iGraphics);
            }
    }

    //llama al update de todos los GO asociados

    public void updateScene(AndroidEngine IEngine, float deltaTime) {
        for (IGameObject gO : GOList) {
            gO.update(IEngine, deltaTime);
        }

        SceneManager.getInstance().procceseMessages();
    }

    public void sendMessageToGO(Message m) {
        for (int i = 0; i < GOMessageList.size(); i++) {
            if (i < GOMessageList.size() && GOMessageList.get(i) != null)
                GOMessageList.get(i).receiveMessage(m);
        }
    }

    //pasa el input a todos los GO de la escena, se detiene si uno lo da como suyo
    public void handleInput(ArrayList<TouchEvent> events) {

        for (TouchEvent e : events) {
            for (IGameObject gO : GOList) {
                if (gO.handleInput(e)) break;
            }
        }

    }
}
