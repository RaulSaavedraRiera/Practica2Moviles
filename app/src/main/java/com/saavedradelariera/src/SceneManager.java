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
    public void Init(AndroidEngine e) {
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
    public void SetScene(Scene scene) //boolean clearWithDelay
    {
        if(engine == null)
            throw new RuntimeException("no seteado el engine");

        activeScene = scene;
        //limpia los objetos registrados a mensajes de la anterior escena
        messagesGO.clear();

        //inicialzia la escena y se la pasa al engine
        activeScene.SetScene(engine.GetGraphics(), engine.GetAudioSystem());
        engine.SetScene(activeScene);
    }

    //lanza una nueva escena al motor y la inicializa
    public void ReturnToScene(Scene scene) //boolean clearWithDelay
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

    public void AddGameObjectToActiveScene(IGameObject gO){
        activeScene.AddGO(gO);
    }

    public void SendMessageToActiveScene(Message m)
    {
        messages.add(m);
    }

    public void ProcceseMessages()
    {
        for (Message m: messages) {
            activeScene.SendMessageToGO(m);
        }

        messages.clear();
    }

    public void RegisterToMessage(IGameObject gO)
    {
        activeScene.AddGOToMessages(gO);
    }

    public void UnRegisterToMessage(IGameObject gO)
    {
        messagesGO.remove(gO);
    }

    public void pushSceneStack(){
        sceneStack.add(activeScene);
    }
    public Scene useSceneStack(){
        Scene aux = sceneStack.peek();
        sceneStack.pop();
        return aux;
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

    public void LaunchAcceleratorEvent()
    {
      SendMessageToActiveScene(new AcceleratorEventMessage());
    }

    public  String  GetActiveSceneState (){
        return activeScene.GetStateScene();
    }
}
