package com.practica1.pool;

import com.practica1.input.TouchEvent;

/*Clase base para la creacion y funcionamiento de la pool*/
public class Pool{

    private int size;
    private TouchEvent[] pool;
    int id = 0;
    int actSize;

    //Incializacion de la pool asi como marcar todos sus elementos como libres
    public Pool(int pSize)
    {
        size = pSize;
        pool = new TouchEvent[size];
        for(int i = 0; i < size; i++)
        {
            pool[i] = new TouchEvent();
            pool[i].free();
        }

    }

    //Creacion de un nuevo evento para la pool
    public TouchEvent newEvent()
    {
        //Si la pool esta toda ocupada devolvemos null
        if(actSize == size)
            return null;

        int i = id;
        while(!pool[i].avaible()){
            //Buscamos cual es el siguiente hueco en la pool
            i = (i+1)%size;

            //Si es igual al id no hay más sitio en la pool
            if(i == id)
                return null;
        }

        actSize++;
        pool[i].allocate();
        return pool[i];
    }

    //Liberamos el evento
    public void freeEvent(TouchEvent e){
        e.free();
        actSize--;
    }

}
