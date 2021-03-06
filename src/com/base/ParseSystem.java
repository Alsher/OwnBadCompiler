package com.base;

import com.base.Indexed.Actions.ActionOut;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectRaw;
import com.base.Indexed.Objects.ObjectReturn;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.HashMap;

/*
    The ParseSystem parses every object in every method provided by the HashSystem.
    It parses raw objects into non-raw objects, eg ObjectInteger or ObjectString.

    As the Compiler needs to be as fast as possible, the ParseSystem also sets
    digit-only values to Integers and sets returnValues.
 */

public class ParseSystem {

    static ArrayList<IndexedObject> objectArrayList = new ArrayList<>();
    static ArrayList<IndexedObject> parameterList = new ArrayList<>();

    public static HashMap<String, IndexedMethod> parseMethods(HashMap<String, IndexedMethod> input)
    {
        HashMap<String, IndexedMethod> returnHash = new HashMap<>();

        //iterate through input accessed by input.values()
        for(IndexedMethod method : input.values())
        {
            /** check for objects **/
            for(IndexedObject object : method.getObjects())
                parseObject(method, object);

            /** check for parameters **/
            if(method.hasParameter()) {
                for (IndexedObject object : method.getParameters())
                    parseParameter(method, object);

                ArrayList<IndexedObject> parameterMap = new ArrayList<>();
                for (IndexedObject object : parameterList)
                    parameterMap.add(object);

                method.setParameters(parameterMap);       //add parsed Parameters to current method
            }

            method.setObjects(objectArrayList);          //add parsed Objects to current method

            returnHash.put(method.getName(), method);    //add everything to the returnHash
            objectArrayList = new ArrayList<>();         //clear objects ArrayList
            parameterList = new ArrayList<>();           //clear parameter ArrayList
        }
        return returnHash;
    }

    private static void parseObject(IndexedMethod rootMethod, IndexedObject object)
    {
        String line = object.getValue().toString();
        String[] tokens = line.split(" ");

        //check for object identifier and parse according to it
        switch(tokens[0])
        {
            case "int":    parseInteger(rootMethod, line, object.getLineNumber(), objectArrayList); break;
            case "String": parseString(rootMethod, line, object.getLineNumber(), objectArrayList); break;
            case "return": parseReturn(rootMethod, line, object.getLineNumber()); break;
            case "action": parseAction(rootMethod, line, object.getLineNumber()); break;

            default: parseDefault(Util.removeCharacter(object.getValue().toString(), ';'), object.getLineNumber()); break;
        }
    }

    private static void parseInteger(IndexedMethod rootMethod, String line, int lineNumber, ArrayList<IndexedObject> listType)
    {
        String[] tokens = line.split(" ");
        ObjectInteger object = new ObjectInteger();

        object.setLineNumber(lineNumber);                       //set line number
        object.setName(tokens[1]);                              //set var name

        if(Util.isInitialization(line)) {
            if (!Util.containsNonMathType(Util.removeCharacters(Util.toUsefulString(tokens), ';', '[', ']')))            //check if value is digit-only | contains no possible method call
            {
                object.setIntValue(MathSystem.calculate(rootMethod, tokens, false));     //do the math and add it returnInteger
                object.setNeedsCompiler(false);                                        //flag the object as non-compiling
                rootMethod.getVariables().put(object.getName(), object);
            } else {
                String stringValue = line.substring(Util.getEqualOperator(line) + 1, line.length() - 1).trim();
                object.setStringValue(stringValue); //add ObjectInteger with complete line as StringValue
                rootMethod.addVariable(object.getName(), object);
            }
        }
        else
        {
            //use value variable as name placeholder
            object.setStringValue(object.getName());
        }
        listType.add(object);
    }

    private static void parseString(IndexedMethod rootMethod, String line, int lineNumber, ArrayList<IndexedObject> listType)
    {
        String[] tokens = line.split(" ");
        ObjectString object = new ObjectString();

        object.setLineNumber(lineNumber);                        //set line number
        object.setName(tokens[1]);                               //set var name

        if(Util.isInitialization(line)) {
            if (!line.contains("(") && line.contains("\"") && !Util.isAMathOperator(line)) {
                object.setContent(Util.getMarkedString(tokens));     //set content to everything in side  ".."
                object.setNeedsCompiler(false);                      //flag the object as non-compiling
                rootMethod.getVariables().put(object.getName(), object); //add to rootMethod
            } else {
                object.setContent(Util.removeCharacters(Util.toUsefulString(tokens), '[', ']')); //add ObjectString with complete line as context
                rootMethod.addVariable(object.getName(), object);                                 //will be compiled later
            }
        }
        else
        {
            //use value variable as name placeholder
            object.setContent(object.getName());
        }
        listType.add(object);
    }

    private static void parseReturn(IndexedMethod rootMethod, String line, int lineNumber)
    {
        String[] tokens = line.split(" ");
        ObjectReturn object = new ObjectReturn();

        tokens[1] = Util.removeCharacter(tokens[1], ';');          //removes semicolon for proper variables.get() call

        object.setLineNumber(lineNumber);

        IndexedObject variable = rootMethod.getVariables().get(tokens[1]);     //create a local indexedObject to prevent unnecessary variables.get() call

        if(variable != null && !variable.needsCompiler())
        {
            object.setReturnObject(variable);
            object.setNeedsCompiler(false);
        }
        else
        {
            object.setContent(line);
            object.setNeedsCompiler(true);
        }

        rootMethod.setReturnObject(object);
        objectArrayList.add(object);
    }

    private static void parseAction(IndexedMethod rootMethod, String line, int lineNumber)
    {
        String[] tokens = StringSystem.smartSplit(line);

        switch (tokens[1])
        {
            case "out": parseActionOut(rootMethod, tokens, lineNumber);
            default: break;
        }
    }

    private static void parseActionOut(IndexedMethod rootMethod, String[] tokens, int lineNumber)
    {
        ActionOut action = new ActionOut();

        action.setLineNumber(lineNumber);

        String[] parameter = new String[tokens.length - 2];

        for(int i = 2; i < tokens.length; i++)
        {
            String s = tokens[i];

            if(s.endsWith(";"))
                s = Util.removeCharacter(s, ';');

            parameter[i - 2] = s;
        }
        action.setParameter(parameter);
        objectArrayList.add(action);
    }

    private static void parseParameter(IndexedMethod rootMethod, IndexedObject object)
    {
        String line = object.getValue().toString();
        String[] tokens = line.split(" ");

        //check for object identifier and parse according to it
        switch(tokens[0])
        {
            case "int":   parseInteger(rootMethod, line, object.getLineNumber(), parameterList); break;
            case "String": parseString(rootMethod, line, object.getLineNumber(), parameterList); break;
        }
    }

    private static void parseDefault(String content, int lineNumber)
    {
        ObjectRaw object = new ObjectRaw(lineNumber, content);

        if(content.matches("[a-zA-Z]\\s[=]\\s[0-9a-zA-Z]"))
            object.setAdditionalInfo(IndexedObject.VAR_TYPE_RAW);
        objectArrayList.add(new ObjectRaw(lineNumber, content));
    }
}
