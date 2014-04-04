package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectRaw;
import com.base.Indexed.Objects.ObjectReturn;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
    The ParseSystem parses every object in every method provided by the HashSystem.
    It parses raw objects into non-raw objects, eg ObjectInteger or ObjectString.

    As the Compiler needs to be as fast as possible, the ParseSystem also sets
    digit-only values to Integers and sets returnValues.
 */

public class ParseSystem {

    static ArrayList<IndexedObject> objects = new ArrayList<>();
    static HashMap<String, IndexedObject> variables = new HashMap<>();

    public static HashMap<String, IndexedMethod> parseMethods(HashMap<String, IndexedMethod> input)
    {
        double startTime = System.nanoTime();

        HashMap<String, IndexedMethod> returnHash = new HashMap<>();

        //access every object in HashMap by its key
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
        String line = object.getValue().toString();
        String[] tokens = line.split(" ");

        //check for object identifier and parse according to it
        switch(tokens[0])
        {
            case "int": parseInteger(line, object.getLineNumber()); break;
            case "String": parseString(line, object.getLineNumber()); break;
            case "return": parseReturn(line, object.getLineNumber()); break;

            default: parseDefault(Util.removeCharacter(object.getValue().toString(), ';'), object.getLineNumber()); break;
        }
    }

    private static void parseInteger(String line, int lineNumber)
    {
        String[] tokens = line.split(" ");
        ObjectInteger object = new ObjectInteger();
        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokens));
        list = Util.removeFromTo(list, 0, 2);                   //remove from index 0 to index 2

        object.setLineNumber(lineNumber);                       //set line number
        object.setName(tokens[1]);                              //set var name

        if(!Util.containsPossibleMethodCall(tokens))            //check if value is digit-only | contains no possible method call
        {
            object.setIntValue(MathSystem.calculate(list));     //do the math and add it returnInteger
            object.setNeedsCompiler(false);                     //flag the object as non-compiling
            variables.put(object.getName(), object);
        }
        else
            variables.put(object.getName(), new ObjectRaw(object.getLineNumber(), line)); //add raw object to HashMap if the Integer does have a method call
    }

    private static void parseString(String line, int lineNumber)
    {
        String[] tokens = line.split(" ");
        ObjectString object = new ObjectString();

        object.setLineNumber(lineNumber);                        //set line number
        object.setName(tokens[1]);                               //set var name
        if(!Util.containsPossibleMethodCall(tokens))
        {
            object.setContent(Util.getMarkedString(tokens));     //set content to everything in side  "[..]"
            object.setNeedsCompiler(false);                      //flag the object as non-compiling
            variables.put(object.getName(), object);
        }
        else
            variables.put(object.getName(), new ObjectRaw(object.getLineNumber(), line)); //add raw object to HashMap if the Integer does have a method call
    }

    private static void parseReturn(String line, int lineNumber)
    {
        String[] tokens = line.split(" ");
        ObjectReturn object = new ObjectReturn();

        tokens[1] = Util.removeCharacter(tokens[1], ';');          //removes semicolon for proper variables.get() call

        object.setLineNumber(lineNumber);

        IndexedObject variableName = variables.get(tokens[1]);     //create a local indexedObject to prevent unnecessary variables.get() call

        if(variableName != null)                                   //checks for an invalid variable name
            object.setReturnObject(variableName);
        else
            System.err.println("Error: \"" + tokens[1] + "\" is not an introduced variable!");

        object.setNeedsCompiler(false);                            //flag the object as non-compiling
        objects.add(object);
    }

    private static void parseDefault(String content, int lineNumber)
    {
        objects.add(new ObjectRaw(lineNumber, content));
    }


}
