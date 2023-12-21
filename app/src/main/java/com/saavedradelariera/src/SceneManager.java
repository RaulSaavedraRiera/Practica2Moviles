package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.messages.AcceleratorEventMessage;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.scenes.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*Clase que gestiona las escenas del juego para facilitar su funcionamiento y relacion con el motor de
de tecnología. Permite lanzar nuevas ecenas, mandar mensajes y añadir objetos a la escena*/
public class SceneManager {
    private static SceneManager instance = null;
    AndroidEngine engine;

    //escena activa
    Scene activeScene = null;
    Stack<Scene> sceneStack = new Stack<>();
    ArrayList<GameObject> messagesGO = new ArrayList<>();

    List<Message> messages= new ArrayList<>();
    //constructor vacio
    private void SceneManager(){}
    //incialziacion en dos pasos
    public void init(AndroidEngine e) {
        engine = e;
    }

    // Método estático para obtener la instancia única
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    //lanza una nueva escena al motor y la inicializa
    public void setScene(Scene scene) //boolean clearWithDelay
    {
        if(engine == null)
            throw new RuntimeException("no seteado el engine");

        activeScene = scene;
        //limpia los objetos registrados a mensajes de la anterior escena
        messagesGO.clear();

        //inicialzia la escena y se la pasa al engine
        activeScene.setScene(engine.GetGraphics(), engine.GetAudioSystem());
        engine.SetScene(activeScene);
    }

    //lanza una nueva escena al motor y la inicializa
    public void returnToScene(Scene scene) //boolean clearWithDelay
    {
        if(engine == null)
            throw new RuntimeException("no seteado el engine");

        activeScene = scene;
        //limpia los objetos registrados a mensajes de la anterior escena
        messagesGO.clear();

        //inicialzia la escena y se la pasa al engine
        //activeScene.ReturnScene(engine.GetGraphics(), engine.GetAudioSystem());
        engine.SetScene(activeScene);
    }

    public AndroidEngine getEngine() {
        return engine;
    }

    public void addGameObjectToActiveScene(IGameObject gO){
        activeScene.addGO(gO);
    }

    public void removeGameObjectFromActiveScene(IGameObject gO){
        activeScene.removeGO(gO);
    }

    public void sendMessageToActiveScene(Message m)
    {
        messages.add(m);
    }

    public void procceseMessages()
    {
        for (Message m: messages) {
            activeScene.sendMessageToGO(m);
        }

        messages.clear();
    }

    public void registerToMessage(IGameObject gO)
    {
        activeScene.addGOToMessages(gO);
    }

    public void unRegisterToMessage(IGameObject gO)
    {
        messagesGO.remove(gO);
    }

    public void pushSceneStack(){
        sceneStack.add(activeScene);
    }
    public Scene useSceneStack(){

        if(!sceneStack.empty())
        {
            Scene aux = sceneStack.peek();
            sceneStack.pop();
            return aux;
        }
        return null;
    }
    public void resetStack()
    {
        sceneStack.clear();
    }

    public Scene getPeckStack()
    {
        if(sceneStack.empty())
            return null;

        return sceneStack.peek();
    }

    public void launchAcceleratorEvent()
    {
        sendMessageToActiveScene(new AcceleratorEventMessage());
    }

    public  String getActiveSceneState(){
        return activeScene.getStateScene();
    }
}
