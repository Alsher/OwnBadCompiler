package com.base;

import com.base.Indexed.IndexedObject;

import java.util.ArrayList;
import java.util.Arrays;

//TODO: enhance the calculate function

public class MathSystem {

    public static Integer calculate(ArrayList<String> input) //does currently only work for positive and negative values / + and - operators
    {
        int returnInt = 0;

        ArrayList<Integer> pureInteger = new ArrayList<>();

        for(int i = 0; i < input.size(); i++)
        {
            boolean hasAlreadyBeenAdded = false;
            IndexedObject mathObject = ParseSystem.variables.get(input.get(i));

            if(input.get(i).equals("-"))
            {
                if(mathObject != null)
                {
                    pureInteger.add(-(Integer) ParseSystem.variables.get(input.get(i + 1)).getValue());
                    i++;
                    hasAlreadyBeenAdded = true;
                }
                else
                {
                    pureInteger.add(-Integer.parseInt(input.get(i + 1)));
                    i++;
                    hasAlreadyBeenAdded = true;
                }
            }

            if(mathObject != null)
               if(mathObject.getType().equals("int") && !hasAlreadyBeenAdded)
                {
                    pureInteger.add((Integer)mathObject.getValue());
                    hasAlreadyBeenAdded = true;
                }
               else {
                   System.err.println("Error: " + mathObject + " is not a mathematical term!");
                    continue;
               }

            if((Util.isInteger(input.get(i)) || (!Util.isInteger(input.get(i)) && !Util.isAMathOperator(input.get(i)))) && !hasAlreadyBeenAdded)
                pureInteger.add(Integer.parseInt(input.get(i)));
        }

        for(Integer integ : pureInteger)
            returnInt += integ;

        return returnInt;
    }

    public static Integer calculate(String[] input)
    {
        return calculate(new ArrayList<>(Arrays.asList(input)));
    }

    public static Integer calculate(String[] input, int excludeStart, int excludeEnd)
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(input));
        list = Util.removeFromTo(list, excludeStart, excludeEnd);
        list = Util.removeSemicolon(list);
        return calculate(list);
    }

}
