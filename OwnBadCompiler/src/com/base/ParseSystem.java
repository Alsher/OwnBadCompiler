package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectReturn;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class ParseSystem {

    static ArrayList<IndexedObject> objects = new ArrayList<>();
    static HashMap<String, IndexedObject> variables = new HashMap<>();

    public static HashMap<String, IndexedMethod> parseMethods(HashMap<String, IndexedMethod> input)
    {
        HashMap<String, IndexedMethod> returnHash = new HashMap<>();
        double startTime = System.nanoTime();

        List<String> keys = new ArrayList<>(input.keySet());
        for(String key : keys)
        {
            IndexedMethod methods = input.get(key);
            for(IndexedObject object : input.get(key).getObjects())
                parseObject(object);

            methods.setObjects(objects);                //add parsed Objects (parsed by the parseObject() hierarchy
            methods.setVariables(variables);            //add parsed Variables (parsed by the parseObject() hierarchy
            returnHash.put(methods.getName(), methods); //add everything to the returnHash
            objects = new ArrayList<>();                //clear objects ArrayList
            variables = new HashMap<>();                //clear variables HashMap
        }

        System.out.println("ParseSystem preReturn takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
        return returnHash;
    }

    private static void parseObject(IndexedObject object)
    {
        String[] tokens = object.getValue().toString().split(" ");

        switch(tokens[0])
        {
            case "int": parseInteger(tokens, object.getLineNumber()); break;
            case "String": parseString(tokens, object.getLineNumber()); break;
            case "return": parseReturn(tokens, object.getLineNumber()); break;

            default: parseDefault(tokens, object.getLineNumber()); break;
        }
    }

    private static void parseInteger(String[] tokens, int lineNumber)
    {
        ObjectInteger object = new ObjectInteger();
        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokens));
        list = Util.removeFromTo(list, 0, 2);               //remove everything before the "=" operator, also including the operator
        list = Util.removeSemicolon(list);                  //remove the semicolon to prevent a nuke in the MathSystem

        object.setLineNumber(lineNumber);                   //set line number
        object.setName(tokens[1]);                          //set var name
        object.setIntValue(MathSystem.calculate(list));     //do the math and add it returnInteger

        variables.put(object.getName(), object);
    }

    private static void parseString(String[] tokens, int lineNumber)
    {
        ObjectString object = new ObjectString();

        object.setLineNumber(lineNumber);               //set line number
        object.setName(tokens[1]);                           //set var name
        object.setContent(Util.getMarkedString(Arrays.toString(tokens)));  //set content

        variables.put(object.getName(), object);
    }

    private static void parseReturn(String[] tokens, int lineNumber)
    {
        ObjectReturn object = new ObjectReturn();

        tokens[1] = Util.removeSemicolon(tokens[1]);      //removes semicolon for proper variables.get() call

        object.setLineNumber(lineNumber);

        IndexedObject variableName = variables.get(tokens[1]); //create a local indexedObject to prevent unnecessary variables.get() call

        if(variableName != null)                                    //checks for an invalid variable name
            object.setReturnObject(variableName);
        else
            System.err.println("Error: \"" + tokens[1] + "\" is not an introduced variable!");

        objects.add(object);
    }

    private static void parseDefault(String[] tokens, int lineNumber)
    {
        IndexedObject object = variables.get(tokens[0]);
        if(object != null && Util.isInteger(object.getValue().toString()))
        {
            ObjectInteger integerObject = new ObjectInteger(tokens[0], variables.get(tokens[0]));
            integerObject.setIntValue(MathSystem.calculate(tokens, 0, 1));
            object = integerObject;
        }
        objects.add(object);
    }
}
