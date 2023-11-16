package com.practica1.desktopengine;

/**
 * Clase base para la creacion y funcionamiento de la pool de sonidos en desktop
 */
public class PoolSoundsDesktop {

    private int size;
    private DesktopSound[] pool;
    int id = 0;
    int actSize;

    //Incializacion de la pool asi como marcar todos sus elementos como libres
    public PoolSoundsDesktop(int pSize)
    {
        size = pSize;
        pool = new DesktopSound[size];
        for(int i = 0; i < size; i++)
        {
            //Valores de inicio para los primeros elementos de la pool
            pool[i] = new DesktopSound(".", false);
            pool[i].free();
        }

    }

    //Creacion de un nuevo evento para la pool
    public DesktopSound newEvent(String name)
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
    public void freeEvent(DesktopSound e){
        e.free();
        actSize--;
    }

}
