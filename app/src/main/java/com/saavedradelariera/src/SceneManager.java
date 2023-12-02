package com.saavedradelariera.src;

import com.practica1.androidengine.AndroidEngine;
import com.saavedradelariera.src.messages.Message;
import com.saavedradelariera.src.scenes.Scene;

import java.util.ArrayList;
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

    public AndroidEngine getEngine() {
        return engine;
    }

    public void AddGameObjectToActiveScene(IGameObject gO){
        activeScene.AddGO(gO);
    }

    public void SendMessageToActiveScene(Message m)
    {
        for (int i = 0; i < messagesGO.size(); i++) {

            if(i < messagesGO.size())
                messagesGO.get(i).ReceiveMessage(m);
        }
    }

    public void RegisterToMessage(GameObject gO)
    {
        messagesGO.add(gO);
    }

    public void UnRegisterToMessage(GameObject gO)
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
        sceneStack.clear();;
    }
}
