package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;

import java.util.ArrayList;
import java.util.HashMap;

/*

 */

public class Compiler {

    static ArrayList<IndexedObject> mainObjects = new ArrayList<>();
    static HashMap<String, IndexedObject> mainVariables = new HashMap<>();
    static HashMap<String, IndexedMethod> methods = new HashMap<>();

    public static void compile(HashMap<String, IndexedMethod> inputHash)
    {
        double startTime = System.nanoTime();

        methods = inputHash;
        mainObjects = Util.toSortetArray(inputHash.get("main").getObjects());
        mainVariables = inputHash.get("main").getVariables();

        for(IndexedObject object : mainObjects)
        {
            object = compileObject(object);
            System.out.println("Post:" + object);
        }

        System.out.println("Compiler takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
    }

    private static IndexedObject compileObject(IndexedObject object)
    {
        String[] tokens = object.getValue().toString().split(" ");
        if(Util.containtsMethodCall(tokens, methods))
            return compileMethodCall(object);

        return null;
    }

    private static IndexedObject compileMethodCall(IndexedObject object)
    {
        return object;
    }

    private static IndexedObject setObjectToMethodCall(IndexedObject object)
    {
        String[] tokens = object.getValue().toString().split(" ");
        if(Util.isInitedVar(mainVariables, tokens[0])) //check if first token is in variables
        {
            if (Util.isAMathOperation(tokens) && Util.isAMethodCall(tokens[2], methods) && Util.isAReturnMethod(tokens[2], methods)) //check if first token is going to be set to a value _and_ if the following token is a method call _and_ if the method call returns
            {
                if (mainVariables.get(tokens[0]).getType().equals(Util.getMethodByKey(tokens[2], methods).getType())) //make sure object type and method return type are equal
                {
                    IndexedMethod method = methods.get(Util.removeBrackets(tokens[2]));
                    method.call();
                    object = new ObjectInteger(object.getLineNumber(), tokens[0], (Integer) method.getReturnObject().getValue());
                }
                //insert error
            }
            //insert error
        }
        //insert error

        return object;
    }

}
