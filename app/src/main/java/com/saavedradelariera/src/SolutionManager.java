package com.saavedradelariera.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*Clase que gestiona la solución: la genera, comprueba si es correcta, etc.*/
public class SolutionManager {

    //generador de  combinaciones aleatorias
    private RandomWinCombination rand = new RandomWinCombination();
    //almacenamos la solución
    ArrayList<Integer> solution = new ArrayList<Integer>();
   Map<Integer, Integer> nTypePerSolution = new HashMap<>();
    //variable con la que podemos determinar si queremos que las pistas indiquen posicion exacta ono
    boolean unordererClue = true;

    //genera la solucion al inicializarse
    public SolutionManager(int diff, boolean loadSolution){
        SetSolution(diff, loadSolution);
    }

    //setea la solución y la añade a su arrayList de solucion
    void SetSolution(int diff, boolean loadSolution)
    {
        int[] aux;

        if(!loadSolution)
        {
            aux = rand.GetWinCombination(diff);
        }
        else
        {
           aux = ProgressManager.getInstance().getLevelInProgressSolution();
        }

        for(int i = 0; i < aux.length; i++)
        {
            solution.add(aux[i]);
            //sumamos cuantos hay de cada color
            if(nTypePerSolution.containsKey(aux[i]))
                nTypePerSolution.put(aux[i], nTypePerSolution.get(aux[i]) + 1);
            else
                nTypePerSolution.put(aux[i], 1);
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
        Map<Integer,Integer> nType = new HashMap<>(nTypePerSolution);

        //comprobamos si la solucion contiene dicho color y si es en esa posición
        for(int i = 0; i < playerTry.size(); i++)
        {
            //el color va ahi
            if(playerTry.get(i) == solution.get(i))
            {
                check.add(0);
                //quitamos uno a los numeros de ese tipo presentes en la solucion
                if(nType.get(playerTry.get(i)) != 0)
                    nType.put(playerTry.get(i), nType.get(playerTry.get(i))- 1);
                //si no quedaban numero es porque ha puesto este numero en otra posicion,
                //para priorizar buenas colocaciones quitamos un 1 correspondiente a este
                else

                    for (int j = 0; j < check.size(); j++)
                        if (check.get(j) == 1) {
                            check.add(2);
                            check.remove(j);
                            break;
                        }


            }
            else if(solution.contains(playerTry.get(i)) && nType.get(playerTry.get(i)) > 0)
            {
                nType.put(playerTry.get(i), nType.get(playerTry.get(i))- 1);

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
    public int getNTypes() { return rand.getNColors();}

    public String getSolutionData() {
        String s = "";
        s+= solution.size();
        for (int i = 0; i < solution.size(); i++)
           s+= String.valueOf(solution.get(i));
        return s;
    }
}
