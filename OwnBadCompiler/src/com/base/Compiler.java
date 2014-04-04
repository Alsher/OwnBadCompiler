package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.HashMap;

/*
    The compiler finally compiles every object that needs to be compiled.
    The compiler is the place for if checks to be executed or loops to be done.

    As the compiler needs to be very reliable and fast for reducing possible
    lag by using it, it _has_ to be lightweight and optimized.

    A function to compile specific methods will be added.

    For example one method inside this language hooks into a Game Mechanic.
    This method therefor needs to be compiled every time the normal Game Mechanic
    method would be called.
    This plan needs further optimization as it _will_ produce ridiculous performance issues
    with the current system.

    The compiler is _not even close_ to be somewhat finished
 */

public class Compiler {

    static ArrayList<IndexedObject> mainObjects = new ArrayList<>();
    static HashMap<String, IndexedObject> mainVariables = new HashMap<>();
    static HashMap<String, IndexedMethod> methods = new HashMap<>();

    public static void compile(HashMap<String, IndexedMethod> inputHash)
    {
        double startTime = System.nanoTime();

        //make inputHash available for other methods
        methods = inputHash;

        //get mainObjects and mainVariables from methods
        mainObjects = methods.get("main").getCombinedObjects();
        mainVariables = methods.get("main").getVariables();

        ArrayList<IndexedObject> compiledObjects = new ArrayList<>();

        //compile every object in mainObjects that has the needsCompiler flag
        for (IndexedObject mainObject : mainObjects)
            if (mainObject.needsCompiler())
                compiledObjects.add(compileObject(mainObject));

        //output
        for(IndexedObject object : Util.toSortedArray(compiledObjects))
            System.out.println("Post:" + object);

        System.out.println("Compiler takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
    }

    private static IndexedObject compileObject(IndexedObject object)
    {
        String[] tokens = object.getValue().toString().split(" ");
        tokens = Util.removeCharacter(tokens, ';');

        //check if input contains a method call
        if(Util.containsMethodCall(tokens, methods))
            return compileMethodCall(object, tokens);

        //TODO: add further compile checks (eg. ifs or loops)

        //loop failed so return null
        return null;
    }

    private static IndexedObject compileMethodCall(IndexedObject object, String[] tokens)
    {
        if(Util.isAVariableAssignment(tokens))                  //check if the line assigns something to a variable
            if(Util.isVariableIniter(tokens))                   //check if the variable is getting initialized
            {
                tokens = Util.removeFromTo(tokens, 0, 1);       //remove the initialization step (eg. [int integer = call();] to internal [integer = call();])
                object = setObjectToMethodCall(object, tokens); //assign a new, by setObjectToMethodCall() compiled IndexedObject to object
            }
            else
                object = setObjectToMethodCall(object, tokens); //assign a new, by setObjectToMethodCall() compiled IndexedObject to object

        return object;
    }

    private static IndexedObject setObjectToMethodCall(IndexedObject object, String[] tokens)
    {
        double start = System.nanoTime();

        if (Util.isAReturnMethod(tokens[2], methods))                                   //check if the called method returns something
            switch (Util.getMethodByKey(tokens[2], methods).getType())                  //check for the method type
            {
                case "int": {
                    IndexedMethod method = methods.get(Util.removeBrackets(tokens[2])); //create local method
                    method.call();                                                      //call this method
                    object = new ObjectInteger(object.getLineNumber(), tokens[0], (Integer) method.getReturnObject().getValue()); //set object to new new ObjectInteger with compiled method values
                } break;

                case "String": {
                    IndexedMethod method = methods.get(Util.removeBrackets(tokens[2])); //create local method
                    method.call();                                                      //call this method
                    object = new ObjectString(object.getLineNumber(), tokens[0], method.getReturnObject().getValue().toString()); //set object to new new ObjectString with compiled method values
                } break;
            }

        else
            ErrorSystem.outputError("method [" + tokens[2] + "] is not a returning method", object.getLineNumber());

        System.out.println("setObjectToMethodCall takes: " + (System.nanoTime() - start)/(double)1000000 + "ms.");
        return object;
    }
}
