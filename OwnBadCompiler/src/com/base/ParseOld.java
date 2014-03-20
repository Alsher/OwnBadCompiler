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


public class ParseOld {

    static ArrayList<IndexedObject> statements = new ArrayList<>();
    static HashMap<String, IndexedObject> variables = new HashMap<>();
    static ArrayList<Integer> bracePosition = new ArrayList<>();

    public static HashMap<String, IndexedMethod> parseMethods(ArrayList<IndexedLine> content)
    {
        HashMap<String, IndexedMethod>  returnMethods = new HashMap<>();
        IndexedMethod method = null;

        /** find and index method **/
        for(IndexedLine cont : content)
        {
            if(Util.isCommentedOut(cont.getLine())) //may eat performance
                continue;

            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": method = Util.getMethod(Util.removeBrackets(tokens), cont.getLineNumber()); break;
                case "+": break; //might me added later

                default: if(Util.isCompleteStatement(tokens)) parseStatement(cont); else System.err.println("Error: \"" + cont.getLine() + "\" is not a complete statement!"); break;
                case "{": bracePosition.add(cont.getLineNumber()); break; //add the first brace position
                case "}":
                    if(method != null) {
                        bracePosition.add(cont.getLineNumber());        //add the second brace position
                        method.setBraceStart(bracePosition.get(0));  //set brace positions in preReturn
                        method.setBraceEnd(bracePosition.get(1));    //set brace positions in preReturn
                        method.setObjects(statements);                //set statements in preReturn to local statements array
                        method.setVariables(variables);              //set variables in preReturn to local variable HashMap

                        bracePosition = new ArrayList<>();              //clear bracePosition array
                        statements = new ArrayList<>();                 //clear statements
                        variables = new HashMap<>();                   //clear variables
                        returnMethods.put(method.getName(), method);}
                    method = null;
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

            default: parseDefault(line); break;
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

//        TODO: add nonInteger return check
//        TODO: add multiphase parsing system
//        System.out.println(Util.removeBrackets(Util.removeSemicolon(list.get(list.size() - 1))));
//        IndexedMethod method = Reader.getHashedMethods().get(Util.removeBrackets(Util.removeSemicolon(list.get(list.size() - 1))));
//        if(method != null)
//            object.setIntValue((Integer)method.getReturnObject().getValue());

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
        ObjectReturn object = new ObjectReturn();

        String[] tokenString = line.getLine().split(" ");
        tokenString[1] = Util.removeSemicolon(tokenString[1]);      //removes semicolon for proper variables.get() call

        object.setLineNumber(line.getLineNumber());

        IndexedObject variableName = variables.get(tokenString[1]); //create a local indexedObject to prevent unnecessary variables.get() call

        if(variableName != null)                                    //checks for an invalid variable name
            object.setReturnObject(variableName);
        else
            System.err.println("Error: \"" + tokenString[1] + "\" is not an introduced variable!");

        statements.add(object);
    }

    private static void parseDefault(IndexedLine line)
    {
        String[] tokenString = line.getLine().split(" ");

        IndexedObject object = variables.get(tokenString[0]);
        if(object != null && Util.isInteger(object.getValue().toString()))
        {
            ObjectInteger integerObject = new ObjectInteger(tokenString[0], variables.get(tokenString[0]));
            integerObject.setIntValue(MathSystem.calculate(tokenString, 0, 1));
            variables.put(tokenString[0], integerObject);
        }
    }
}
