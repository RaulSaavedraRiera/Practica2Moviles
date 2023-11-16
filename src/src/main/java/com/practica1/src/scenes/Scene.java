package com.practica1.src.scenes;

import com.practica1.engine.IEngine;
import com.practica1.src.IGameObject;
import com.practica1.engine.IScene;
import com.practica1.graphics.IGraphics;
import com.practica1.input.TouchEvent;
import com.practica1.sound.IAudioSystem;
import com.practica1.src.AudioManager;
import com.practica1.src.SceneManager;

import java.util.ArrayList;

/*Clase scene abstracta que inicialia la estructura comun*/
public abstract class Scene implements IScene {
    protected String name = "";
    protected ArrayList<IGameObject> GOList = new ArrayList<IGameObject>();

    //inicialzia la escena generando su audiomanager
    @Override
    public void SetScene(IGraphics graphics, IAudioSystem audioSystem){
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
    public void RenderScene(IGraphics iGraphics){
        for (IGameObject gO : GOList)
        {
            gO.Render(iGraphics);
        }
    }

    //llama al update de todos los GO asociados
    @Override
    public void UpdateScene(IEngine IEngine, float deltaTime){
        for (IGameObject gO : GOList)
        {
            gO.Update(IEngine, deltaTime);
        }
    }

    //pasa el input a todos los GO de la escena, se detiene si uno lo da como suyo
    @Override
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
