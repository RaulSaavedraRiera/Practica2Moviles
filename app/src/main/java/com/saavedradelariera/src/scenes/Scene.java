package com.saavedradelariera.src.scenes;

import com.practica1.androidengine.AndroidAudio;
import com.practica1.androidengine.AndroidEngine;
import com.practica1.androidengine.AndroidGraphics;
import com.practica1.androidengine.IScene;
import com.practica1.androidengine.TouchEvent;
import com.saavedradelariera.src.IGameObject;
import com.saavedradelariera.src.AudioManager;
import com.saavedradelariera.src.SceneManager;

import java.util.ArrayList;

/*Clase scene abstracta que inicialia la estructura comun*/
public abstract class Scene implements IScene {
    protected String name = "";
    protected ArrayList<IGameObject> GOList = new ArrayList<IGameObject>();

    //inicialzia la escena generando su audiomanager

    public void SetScene(AndroidEngine graphics, AndroidAudio audioSystem){
        AudioManager aM = new AudioManager(audioSystem);
        AddGO(aM);
        SceneManager.getInstance().RegisterToMessage(aM);
    }

    public void AddGO(IGameObject g){
        GOList.add(g);
    }

    public  void RemoveGO(IGameObject g){
        GOList.remove(g);
    }

    @Override
    public String GetName(){
        return name;
    }

    @Override
    public void SetName(String n){
        name = n;
    }

    //llama al render de todos los GO asociados
    @Override
    public void RenderScene(AndroidGraphics iGraphics){
        for (IGameObject gO : GOList)
        {
            gO.Render(iGraphics);
        }
    }

    //llama al update de todos los GO asociados

    public void UpdateScene(AndroidEngine IEngine, float deltaTime){
        for (IGameObject gO : GOList)
        {
            gO.Update(IEngine, deltaTime);
        }
    }

    //pasa el input a todos los GO de la escena, se detiene si uno lo da como suyo

    public void HandleInput(ArrayList<TouchEvent> events){

        for(TouchEvent e : events)
        {
            for(IGameObject gO : GOList)
            {
                if(gO.HandleInput(e)) break;
            }
        }

    }
}
