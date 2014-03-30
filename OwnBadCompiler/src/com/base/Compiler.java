package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectString;

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

        mainObjects.addAll(Util.hashToArray(mainVariables));

        ArrayList<IndexedObject> compiledObjects = new ArrayList<>();

        for (IndexedObject mainObject : mainObjects)
            if (mainObject.needsCompiler())
                compiledObjects.add(compileObject(mainObject));



        for(IndexedObject object : Util.toSortetArray(compiledObjects))
            System.out.println("Post:" + object);

        System.out.println("Compiler takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
    }

    private static IndexedObject compileObject(IndexedObject object)
    {
        String[] tokens = object.getValue().toString().split(" ");
        tokens = Util.removeCharacter(tokens, ';');

        if(Util.containsMethodCall(tokens, methods)) //check if input contains a method call
            return compileMethodCall(object, tokens);

        return null;
    }

    private static IndexedObject compileMethodCall(IndexedObject object, String[] tokens)
    {
        if(Util.isAVariableAssignment(tokens)) //check if the line assigns something to a variable
            if(Util.isVariableIniter(tokens))  //check if the variable is getting initialized
            {
                tokens = Util.removeFromTo(tokens, 0, 1); //remove the initialization step (eg. int integer = call(); to internal integer = call();
                object = setObjectToMethodCall(object, tokens);
            }
            else
                object = setObjectToMethodCall(object, tokens);

        return object;
    }

    private static IndexedObject setObjectToMethodCall(IndexedObject object, String[] tokens)
    {
        if (Util.isAReturnMethod(tokens[2], methods)) //check if the called method returns something
            switch (Util.getMethodByKey(tokens[2], methods).getType()) //check for the method type
            {
                case "int": {
                    IndexedMethod method = methods.get(Util.removeBrackets(tokens[2])); //create local method
                    method.call();                                                      //call this method
                    object = new ObjectInteger(object.getLineNumber(), tokens[0], (Integer) method.getReturnObject().getValue());
                } break;

                case "String": {
                    IndexedMethod method = methods.get(Util.removeBrackets(tokens[2])); //create local method
                    method.call();                                                      //call this method
                    object = new ObjectString(object.getLineNumber(), tokens[0], method.getReturnObject().getValue().toString());
                } break;
            }

        else
            ErrorSystem.outputError("method [" + tokens[2] + "] is not a returning method", object.getLineNumber());

        return object;
    }
}
