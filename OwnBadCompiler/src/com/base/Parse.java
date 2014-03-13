package com.base;

import com.base.Indexed.Actions.ObjectInteger;
import com.base.Indexed.Actions.ObjectString;
import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedStatement;

import java.util.ArrayList;
import java.util.Arrays;


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
            if(Util.isCommentedOut(cont.getLine())) //may eat performance
                continue;

            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": preReturn = new IndexedMethod(cont.getLineNumber(), Util.removeBrackets(tokens[1]), tokens[2]); break;
                case "+": break; //might me added later

                default: statements.add(parseStatement(cont));
                case "{": bracePosition.add(cont.getLineNumber()); break; //add the first brace position
                case "}":
                    bracePosition.add(cont.getLineNumber());        //add the second brace position
                    preReturn.setBraceStart(bracePosition.get(0));  //set brace positions in preReturn
                    preReturn.setBraceEnd(bracePosition.get(1));    //set brace positions in preReturn
                    preReturn.setStatements(statements);            //set statements in preReturn to local statements array

                    bracePosition = new ArrayList<>();              //clear bracePosition array
                    statements = new ArrayList<>();                 //clear statements
                    returnList.add(preReturn);                      //add to returnList, may not be necessary
                    break;
            }
        }
        return returnList;
    }

    private static IndexedStatement parseStatement(IndexedLine line)
    {
        String[] tokensString = line.getLine().split(" ");

        switch(tokensString[0])
        {
            case "int": return new IndexedStatement(new ObjectInteger(parseInteger(line)));
            case "String": return new IndexedStatement(new ObjectString(parseString(line)));
            case "return": return null; //TODO: add return function

            default: return null; //if the line is not recognized it will return null
        }
    }

    private static ObjectInteger parseInteger(IndexedLine line)
    {
        ObjectInteger returnInteger = new ObjectInteger();

        String[] tokenString = line.getLine().split(" ");

        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokenString));
        list = Util.removeFromTo(list, 0, 2);               //remove everything before the "=" operator, also including the operator
        list = Util.removeSemicolon(list);                  //remove the semicolon to prevent a nuke in the MathSystem

        returnInteger.setLineNumber(line.getLineNumber());  //set line number
        returnInteger.setName(tokenString[1]);              //set var name
        returnInteger.setValue(MathSystem.calculate(list)); //do the math and add it returnInteger

        return returnInteger;
    }

    private static ObjectString parseString(IndexedLine line)
    {
        ObjectString returnString = new ObjectString();

        String[] tokenString = line.getLine().split(" ");

        returnString.setLineNumber(line.getLineNumber());               //set line number
        returnString.setName(tokenString[1]);                           //set var name
        returnString.setContent(Util.getMarkedString(line.getLine()));  //set content

        return returnString;
    }

}
