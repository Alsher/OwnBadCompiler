package com.base;

import java.util.ArrayList;
import java.util.Arrays;

public class MathSystem {

    static ArrayList<Integer> pureInteger;

    public static Integer calculate(ArrayList<String> input) //does currently only work for positive and negative values / + and - operators
    {
        int returnInt = 0;

        pureInteger = new ArrayList<>();

        for(int i = 0; i < input.size(); i++)
        {
            boolean hasAlreadyBeenAdded = false;


            if(input.get(i).equals("-"))
            {
                if(Parse.variables.get(input.get(i)) != null)
                {
                    pureInteger.add(-(Integer)Parse.variables.get(input.get(i + 1)).getValue());
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

            if(Parse.variables.get(input.get(i)) != null)
                if((Util.isInteger(Parse.variables.get(input.get(i)).getValue().toString()) || (!Util.isInteger((String)Parse.variables.get(input.get(i)).getValue().toString()) && !Util.isAMathOperator(input.get(i)))) && !hasAlreadyBeenAdded)
                     pureInteger.add((Integer)Parse.variables.get(input.get(i)).getValue());
            else
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
