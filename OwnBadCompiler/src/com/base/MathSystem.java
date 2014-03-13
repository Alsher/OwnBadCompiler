package com.base;


import java.util.ArrayList;

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
                pureInteger.add(-Integer.parseInt(input.get(i + 1)));
                i++;
                hasAlreadyBeenAdded = true;
            }

            if((Util.isInteger(input.get(i)) || (!Util.isInteger(input.get(i)) && !Util.isAMathOperator(input.get(i)))) && !hasAlreadyBeenAdded)
                 pureInteger.add(Integer.parseInt(input.get(i)));
        }

        for(Integer integ : pureInteger)
            returnInt += integ;

        return returnInt;
    }

}
