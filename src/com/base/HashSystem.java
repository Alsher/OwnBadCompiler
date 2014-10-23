package com.base;

import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Methods.MethodInteger;
import com.base.Indexed.Methods.MethodString;
import com.base.Indexed.Methods.MethodVoid;
import com.base.Indexed.Objects.ObjectRaw;

import java.util.ArrayList;
import java.util.HashMap;

/*
    The HashSystem only scans the file for methods and objects and puts these into a HashMap with String to IndexedMethod
    As every IndexedMethod itself contains a HashMap with String to IndexedObject, every so-called "raw" object is assigned
    to its Method.
    The HashSystem is optimized to be fast and lightweight.
 */

public class HashSystem {

    public static HashMap<String, IndexedMethod> hashMethods(ArrayList<IndexedLine> content)
    {
        ArrayList<IndexedObject> rawObjects = new ArrayList<>();
        ArrayList<Integer> bracePosition = new ArrayList<>();
        HashMap<String, IndexedMethod> hashedMethods = new HashMap<>();

        IndexedMethod method = null;

        for(IndexedLine cont : content)
        {
            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": method = getMethod(Util.removeCharacters(cont.getLine(), '(', ')'), cont.getLineNumber()); break;

                default: rawObjects.add(new ObjectRaw(cont.getLineNumber(), cont.getLine())); break;
                case "{": bracePosition.add(cont.getLineNumber()); break; //add the first brace position
                case "}":
                    if(method != null)
                    {
                        bracePosition.add(cont.getLineNumber());        //add the second brace position
                        method.setBraceStart(bracePosition.get(0));     //set brace positions in preReturn
                        method.setBraceEnd(bracePosition.get(1));       //set brace positions in preReturn
                        method.setObjects(rawObjects);                  //set methods empty Object-Set to rawObjects

                        method.setVariables(new HashMap<String, IndexedObject>());

                        bracePosition = new ArrayList<>();              //clear bracePosition array
                        rawObjects = new ArrayList<>();                 //clear rawObjects
                        hashedMethods.put(method.getName(), method);
                    }
                    break;
            }
        }
        return hashedMethods;
    }

    private static IndexedMethod getMethod(String content, int line)
    {
        String[] tokens = content.split(" ");
        int paramPosition = Util.getPosition(content, ':');
        ArrayList<IndexedObject> parameter;

        if((paramPosition + 1) < content.length())
        {
            String[] param = content.substring(paramPosition + 1).split(",");
            param = Util.trimArray(param);

            parameter = new ArrayList<>();

            for(String s : param)
                parameter.add(new ObjectRaw(line, s));

            switch(tokens[1])
            {
                case "void": return new MethodVoid(line, Util.removeCharacter(tokens[2], ':'), parameter);
                case "int" : return new MethodInteger(line, Util.removeCharacter(tokens[2], ':'), parameter);
                case "String" : return new MethodString(line, Util.removeCharacter(tokens[2], ':'), parameter);
                default: return null;
            }
        }
        switch(tokens[1])
        {
            case "void": return new MethodVoid(line, Util.removeCharacter(tokens[2], ':'));
            case "int" : return new MethodInteger(line, Util.removeCharacter(tokens[2], ':'));
            case "String" : return new MethodString(line, Util.removeCharacter(tokens[2], ':'));
            default: return null;
        }
    }
}