package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TODO: enhance the calculate function

public class MathSystem {

    public static Integer calculate(IndexedMethod rootMethod, ArrayList<String> components, boolean containsMethodCall) //does currently only work for positive and negative values / + and - operators
    {

        int equalOpPos = Util.getEqualOperator(components);
//        String equalop = components.get(equalOpPos);
//        System.out.println("MS: " + components + equalop);
        components = Util.removeFromTo(components, 0, equalOpPos);

        HashMap<String, IndexedObject> variables = rootMethod.getVariables();

        components = Util.removeCharacter(components, ';');

        String[] parsedComponents = new String[components.size()];

        //iterate through component list
        for(int i = 0; i < components.size(); i++)
        {
            String component = components.get(i);

            /** check if component is valid integer **/
            if(Util.isInteger(component))
                parsedComponents[i] = component;

            /** if not, check if mathematical operator **/
            else if(Util.isAMathOperator(component))
                parsedComponents[i] = component;

            /** if not, check if component is valid variable **/
            else if(variables.get(component) != null &&
                    variables.get(component) instanceof ObjectInteger)
                parsedComponents[i] = ((ObjectInteger) variables.get(component)).getValue().toString();

            /** if not, check if component is  method call **/
            else if(containsMethodCall && Util.isMethodCall(component)) {
                IndexedMethod method = Compiler.methods.get(Util.removeCharacters(component, '[', '(', ')', ']'));
                method.call();
                parsedComponents[i] = method.getReturnObject().getValue().toString();
            }
        }

        return calculationEngine(parsedComponents);
    }

    public static Integer calculate(IndexedMethod rootMethod, String components, boolean containsMethodCall)
    {
        ArrayList<String> componentList = new ArrayList<>(Arrays.asList(components.split(" ")));
        return calculate(rootMethod, componentList, containsMethodCall);
    }

    public static Integer calculate(IndexedMethod rootMethod, String[] components, boolean containsMethodCall)
    {
        ArrayList<String> componentList = new ArrayList<>(Arrays.asList(Util.toUsefulString(components).split(" ")));
        return calculate(rootMethod, componentList, containsMethodCall);
    }

    private static Integer calculationEngine(String[] input)
    {
        int result = 0;

        for(int i = 0; i < input.length; i++)
        {
            if(Util.isInteger(input[i]))
                result += Integer.parseInt(input[i]);
            else if(input[i].equals("-")) {
                result -= Integer.parseInt(input[i + 1]);
                i++;
            }
            else if(input[i].equals("+")) {
                result += Integer.parseInt(input[i + 1]);
                i++;
            }
        }

        return result;
    }
}
