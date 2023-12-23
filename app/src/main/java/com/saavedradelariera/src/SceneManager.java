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
    //pila de escenas para poder volver para atras
    Stack<Scene> sceneStack = new Stack<>();
    //lista de gameObjects suscritos a mensajes
    ArrayList<GameObject> messagesGO = new ArrayList<>();
    //lista de mensajes para ser mandados en paquete
    List<Message> messages= new ArrayList<>();
    //constructor vacio
    private void SceneManager(){}
    //iniicalizacion en dos pasos
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
        activeScene.setScene(engine.getGraphics(), engine.getAudioSystem());
        engine.setScene(activeScene);
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
        engine.setScene(activeScene);
    }

    public AndroidEngine getEngine() {
        return engine;
    }

    //añade el gameObject a la escena activa
    public void addGameObjectToActiveScene(IGameObject gO){
        activeScene.addGO(gO);
    }
    //elimina el gameObject de la escena activa
    public void removeGameObjectFromActiveScene(IGameObject gO){
        activeScene.removeGO(gO);
    }
    //mueve el gameObject para ser renderizado sobre los demas
    public void moveGOToTopInActiveScene(IGameObject gO){
        activeScene.moveToTopGO(gO);
    }
    //mueve el gameObject para ser renderizado por debajo de los demas
    public void moveGOToBottomInActiveScene(IGameObject gO){
        activeScene.moveToBottonGO(gO);
    }
    //añade el mensaje a la pila de mensajes para ser procesado posteriormente
    public void sendMessageToActiveScene(Message m)
    {
        messages.add(m);
    }

    //procesa los mensajes pendientes enviandolos a los gameobject suscritos en la escena
    public void procceseMessages()
    {
        for (Message m: messages) {
            activeScene.sendMessageToGO(m);
        }

        messages.clear();
    }

    //registra el gameObject a los mensajes
    public void registerToMessage(IGameObject gO)
    {
        activeScene.addGOToMessages(gO);
    }
    //desregistra el gameObject a los mensajes
    public void unRegisterToMessage(IGameObject gO)
    {
        messagesGO.remove(gO);
    }
    //añade la escena activa al stack de escenas
    public void pushSceneStack(){
        sceneStack.add(activeScene);
    }
    //usa la escena almacenada en el stack
    public Scene useSceneStack(){

        if(!sceneStack.empty())
        {
            Scene aux = sceneStack.peek();
            sceneStack.pop();
            return aux;
        }
        return null;
    }
    //resetea el stack de escenas
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

    //lanza un mensaje de evento de aceleracion
    public void launchAcceleratorEvent()
    {
        sendMessageToActiveScene(new AcceleratorEventMessage());
    }

    //obtiene el progreso de la escena activa
    public  String getActiveSceneState(){
        return activeScene.getStateScene();
    }
}
