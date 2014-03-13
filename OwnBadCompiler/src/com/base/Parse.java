package com.base;

import com.base.Indexed.*;
import com.base.Indexed.Actions.ObjectInteger;
import com.base.Indexed.Actions.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Parse{

    public static ArrayList<IndexedMethod> parseMethods(ArrayList<IndexedLine> content)
    {
        ArrayList<IndexedMethod> returnList = new ArrayList<>();

        IndexedMethod preReturn = new IndexedMethod();

        ArrayList<Integer> bracePosition = new ArrayList<>();

        ArrayList<IndexedStatement> statements = new ArrayList<>();

        /** find and index method **/
        for(IndexedLine cont : content)
        {
            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": preReturn = new IndexedMethod(cont.getLineNumber(), Util.removeBrackets(tokens[1]), tokens[2]); break;
                case "+": break; //might me added later

                default: statements.add(parseStatement(cont));
                case "{": bracePosition.add(cont.getLineNumber()); break;
                case "}":
                    bracePosition.add(cont.getLineNumber());
                    preReturn.setBraceStart(bracePosition.get(0));  //set brace positions in preReturn
                    preReturn.setBraceEnd(bracePosition.get(1));    //set brace positions in preReturn
                    preReturn.setStatements(statements);            //set statements in preReturn to local statements array

                    bracePosition = new ArrayList<>();              //clear bracePosition array
                    statements = new ArrayList<>();                 //clear statements
                    returnList.add(preReturn);                      //add to returnList
                    break;
            }
        }
        return returnList;
    }

    public static IndexedStatement parseStatement(IndexedLine line)
    {
        String[] tokensString = line.getLine().split(" ");
        ArrayList<String> tokens = new ArrayList<>();

        Collections.addAll(tokens, tokensString);

        switch(tokens.get(0))
        {
            case "int": return new IndexedStatement(new ObjectInteger(parseInteger(line)));
            case "String": return new IndexedStatement(new ObjectString(parseString(line)));
            case "return": return null;

        }
        System.out.println(tokens);
        return null;
    }

    private static ObjectInteger parseInteger(IndexedLine line)
    {
        ObjectInteger returnInteger = new ObjectInteger();

        String[] tokenString = line.getLine().split(" ");

        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokenString));
        list = Util.removeFromTo(list, 0, 2);
        list = Util.removeSemicolon(list);

        returnInteger.setName(tokenString[1]); //set name
        returnInteger.setLineNumber(line.getLineNumber()); //set line number

        returnInteger.setValue(MathSystem.calculate(list)); //do the math
        return returnInteger;
    }

    private static ObjectString parseString(IndexedLine line)
    {
        ObjectString returnString = new ObjectString();

        String[] tokenString = line.getLine().split(" ");

        returnString.setLineNumber(line.getLineNumber());
        returnString.setName(tokenString[1]);
        returnString.setContent(Util.getMarkedString(line.getLine()));

        return returnString;
    }

}
