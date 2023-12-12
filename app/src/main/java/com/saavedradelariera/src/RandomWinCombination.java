package com.saavedradelariera.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*Clase usada para generar la combinacion ganadora
basada en unos parámetros marcados por la dificultad*/
public class RandomWinCombination {

    int nColors;
    int nSol;
    boolean canRepeat;

    public int[] GetWinCombination(int i){
        //segun la dificultad seleccionada inicializamos las variables iniciales de la manera correspondiente
        switch(i){
            //modo facil
            case 0:
                nColors = 4;
                nSol = 4;
                canRepeat = false;
                break;
            //modo medio
            case 1:
                nColors = 6;
                nSol = 4;
                canRepeat = false;
                break;
            //modo dificil
            case 2:
                nColors = 8;
                nSol = 5;
                canRepeat = true;
                break;
            //modo imposible
            case 3:
                nColors = 9;
                nSol = 6;
                canRepeat = true;
                break;
            case 4:
                nColors = ResourcesManager.getInstance().getActualLevel().getCodeOpt();
                nSol =  ResourcesManager.getInstance().getActualLevel().getCodeSize();
                canRepeat = ResourcesManager.getInstance().getActualLevel().isRepeat();
                break;
        }

      return GenerateWinCombination();
    }

    //obtiene la combinación ganadora
    int[] GenerateWinCombination(){

        //array a devolver y array dinamico con los usados
        int[] winCombination = new int[nSol];
        ArrayList<Integer> used = new ArrayList<>();

        //random y variable temporal
        Random random = new Random();
        int tmp;

        for (int i = 0; i < nSol; i++)
        {
            //si se puede repetir simplemente cogemos un numero aleatorio
            if(canRepeat)
            {
                winCombination[i] = random.nextInt(nColors);
            }
            //si no cogemos un numero aleatorio hasta que salga uno no usado
            else
            {
                do {
                    tmp = random.nextInt(nColors);
                } while(used.contains((tmp)));
                //y lo almacenamos en la combiancion ganadora
                winCombination[i] = tmp;
                used.add(tmp);
            }
        }

        //la devolvemos
        return winCombination;

    }

    public int getNColors() { return nColors;}
}
