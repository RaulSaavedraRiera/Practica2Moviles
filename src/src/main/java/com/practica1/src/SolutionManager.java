package com.practica1.src;

import java.util.ArrayList;
import java.util.Collections;

/*Clase que gestiona la solución: la genera, comprueba si es correcta, etc.*/
public class SolutionManager {

    //generador de  combinaciones aleatorias
    private RandomWinCombination rand = new RandomWinCombination();
    //almacenamos la solución
    ArrayList<Integer> solution = new ArrayList<Integer>();
    //variable con la que podemos determinar si queremos que las pistas indiquen posicion exacta ono
    boolean unordererClue = true;

    //genera la solucion al inicializarse
    public SolutionManager(int diff){
        SetSolution(diff);
    }

    //setea la solución y la añade a su arrayList de solucion
    void SetSolution(int diff)
    {
        int[] aux;
        aux = rand.GetWinCombination(diff);

        for(int i = 0; i < aux.length; i++)
        {
            solution.add(aux[i]);
        }
    }

    /*Devuelve un array que representa la relación entre la solución propuesta y la real.
     * Si son completamente iguales, añade un 99 al final.
     * A la hora de montar el array de la comprobacion los valores usados serán:
     *   - 0: el color se encuentra en la posicion correcta
     *   - 1: el color se encuentra en la solucion pero no en esa posicion
     *   - 2: el color no se encuentra en la solucion
     * */
    public ArrayList<Integer> compareSolution(ArrayList<Integer> playerTry)
    {
        boolean hasWin = true;

        ArrayList<Integer> check = new ArrayList<>();

        //comprobamos si la solucion contiene dicho color y si es en esa posición
        for(int i = 0; i < playerTry.size(); i++)
        {
            if(playerTry.get(i) == solution.get(i))
            {
                check.add(0);
            }
            else if(solution.contains(playerTry.get(i)))
            {
                hasWin = false;
                check.add(1);
            }
            else
            {
                hasWin = false;
                check.add(2);
            }

        }

        //si ha ganado añade -1 al final
        if(hasWin)
        {
           check.add(99);
        }

        //si debe ser desordenado la ordena por aciertos/fallos
        if(unordererClue)
            Collections.sort(check);

        return check;
    }

    //devuelve la solucion
    public ArrayList<Integer> getSolution() {
        return solution;
    }
}
