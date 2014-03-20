package com.base;

import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectRaw;

import java.util.ArrayList;
import java.util.HashMap;

public class HashSystem {

    public static HashMap<String, IndexedMethod> hashMethods(ArrayList<IndexedLine> content)
    {
        ArrayList<IndexedObject> rawObjects = new ArrayList<>();
        ArrayList<Integer> bracePosition = new ArrayList<>();

        double startTime = System.nanoTime();

        HashMap<String, IndexedMethod> hashedMethods = new HashMap<>();
        IndexedMethod method = null;

        for(IndexedLine cont : content)
        {
            String[] tokens = cont.getLine().split(" ");
            switch(tokens[0])
            {
                case "-": method = Util.getMethod(Util.removeBrackets(tokens), cont.getLineNumber()); break;

                default: rawObjects.add(new ObjectRaw(cont.getLineNumber(), cont.getLine())); break;
                case "{": bracePosition.add(cont.getLineNumber()); break; //add the first brace position
                case "}":
                    if(method != null)
                    {
                        bracePosition.add(cont.getLineNumber());        //add the second brace position
                        method.setBraceStart(bracePosition.get(0));     //set brace positions in preReturn
                        method.setBraceEnd(bracePosition.get(1));       //set brace positions in preReturn
                        method.setObjects(rawObjects);                  //set methods empty Object-Set to rawObjects

                        bracePosition = new ArrayList<>();              //clear bracePosition array
                        rawObjects = new ArrayList<>();                 //clear rawObjects
                        hashedMethods.put(method.getName(), method);
                    }
                    break;
            }
        }
        System.out.println("HashSystem preReturn takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
        return hashedMethods;
    }
}
