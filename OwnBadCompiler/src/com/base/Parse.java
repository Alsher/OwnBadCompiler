package com.base;

import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectReturn;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Parse{

    static ArrayList<IndexedObject> statements = new ArrayList<>();
    static HashMap<String, IndexedObject> variables = new HashMap<>();
    static ArrayList<Integer> bracePosition = new ArrayList<>();

    public static ArrayList<IndexedMethod> parseMethods(ArrayList<IndexedLine> content)
    {
        ArrayList<IndexedMethod> returnMethods = new ArrayList<>();
        IndexedMethod preReturn = new IndexedMethod();

        /** find and index method **/
        for(IndexedLine cont : content)
        {
            if(Util.isCommentedOut(cont.getLine())) //may eat performance
                continue;

            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": preReturn = new IndexedMethod(cont.getLineNumber(), Util.removeBrackets(tokens[1]), tokens[2]); break;
                case "+": break; //might me added later

                default: parseStatement(cont);
                case "{": bracePosition.add(cont.getLineNumber()); break; //add the first brace position
                case "}":
                    bracePosition.add(cont.getLineNumber());        //add the second brace position
                    preReturn.setBraceStart(bracePosition.get(0));  //set brace positions in preReturn
                    preReturn.setBraceEnd(bracePosition.get(1));    //set brace positions in preReturn
                    preReturn.setObject(statements);                //set statements in preReturn to local statements array
                    preReturn.setVariables(variables);              //set variables in preReturn to local variable HashMap

                    bracePosition = new ArrayList<>();              //clear bracePosition array
                    statements = new ArrayList<>();                 //clear statements
                    variables = new HashMap<>();                    //clear variables
                    returnMethods.add(preReturn);                   //add to returnMethods, may not be necessary
                    break;
            }
        }
        return returnMethods;
    }

    private static void parseStatement(IndexedLine line)
    {
        String[] tokensString = line.getLine().split(" ");

        switch(tokensString[0])
        {
            case "int": parseInteger(line); break;
            case "String": parseString(line); break;
            case "return": parseReturn(line); break;

            default: break;
        }
    }

    private static void parseInteger(IndexedLine line)
    {
        ObjectInteger object = new ObjectInteger();

        String[] tokenString = line.getLine().split(" ");

        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokenString));
        list = Util.removeFromTo(list, 0, 2);               //remove everything before the "=" operator, also including the operator
        list = Util.removeSemicolon(list);                  //remove the semicolon to prevent a nuke in the MathSystem

        object.setLineNumber(line.getLineNumber());         //set line number
        object.setName(tokenString[1]);                     //set var name
        object.setIntValue(MathSystem.calculate(list));     //do the math and add it returnInteger

        variables.put(object.getName(), object);
    }

    private static void parseString(IndexedLine line)
    {
        ObjectString object = new ObjectString();

        String[] tokenString = line.getLine().split(" ");

        object.setLineNumber(line.getLineNumber());               //set line number
        object.setName(tokenString[1]);                           //set var name
        object.setContent(Util.getMarkedString(line.getLine()));  //set content

        variables.put(object.getName(), object);
    }

    private static void parseReturn(IndexedLine line)
    {
        ObjectReturn returnObject = new ObjectReturn();

        String[] tokenString = line.getLine().split(" ");
        tokenString[1] = Util.removeSemicolon(tokenString[1]);      //removes semicolon for proper variables.get() call

        returnObject.setLineNumber(line.getLineNumber());

        IndexedObject variableName = variables.get(tokenString[1]); //create a local indexedObject to prevent unnecessary variables.get() call

        if(variableName != null)                                    //checks for an invalid variable name
            returnObject.setReturnObject(variableName);
        else
            System.err.println("Error: \"" + tokenString[1] + "\" is not an introduced variable!");

        statements.add(returnObject);
    }
}
