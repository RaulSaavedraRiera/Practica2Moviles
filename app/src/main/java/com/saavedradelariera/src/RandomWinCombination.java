package com.saavedradelariera.src;

import java.util.ArrayList;
import java.util.Random;

/*Clase usada para generar la combinacion ganadora
basada en unos parámetros marcados por la dificultad*/
public class RandomWinCombination {
    int nColors, nSol;
    boolean canRepeat;

    public int[] getWinCombination(int nColors, int nSol, boolean canRepeat) {
        this.nColors = nColors;
        this.nSol = nSol;
        this.canRepeat = canRepeat;

        return generateWinCombination();
    }

    //obtiene la combinación ganadora
    int[] generateWinCombination() {

        //array a devolver y array dinamico con los usados
        int[] winCombination = new int[nSol];
        ArrayList<Integer> used = new ArrayList<>();

        //random y variable temporal
        Random random = new Random();
        int tmp;

        for (int i = 0; i < nSol; i++) {
            //si se puede repetir simplemente cogemos un numero aleatorio
            if (canRepeat) {
                winCombination[i] = random.nextInt(nColors);
            }
            //si no cogemos un numero aleatorio hasta que salga uno no usado
            else {
                do {
                    tmp = random.nextInt(nColors);
                } while (used.contains((tmp)));
                //y lo almacenamos en la combiancion ganadora
                winCombination[i] = tmp;
                used.add(tmp);
            }
        }

        //la devolvemos
        return winCombination;

    }
}
