package com.base;


import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/*
    The Reader is the entry point for the whole class as it runs everything right from the constructor.
    The startTime, endTime etc. is all for performance testing. The console will tell you the speed of
    the program in ms.
*/

public class Reader
{
    private ArrayList<IndexedLine> indexedLines = new ArrayList<>();

    private static HashMap<String, IndexedMethod> hashedMethods = new HashMap<>();

    public Reader(String fileName)
    {
        /** index file **/
        indexContent(fileName);

        /** hash the file  without any further calculation **/
        hashedMethods = HashSystem.hashMethods(indexedLines);

        /** parse methods in hashedMethods **/
        hashedMethods = ParseSystem.parseMethods(hashedMethods);

        /** check for language mistakes **/
        if(ErrorSystem.checkForErrors(hashedMethods) != 1)
        System.err.println("Error: script contains errors");

        /** compile | run **/
        else Compiler.compile(hashedMethods);
    }

    private void indexContent(String fileName)
    {
        String[] splitArray = fileName.split("\\.");
        String extension = splitArray[splitArray.length - 1]; //get file extension

        if(!extension.equals("txt"))
        {
            System.err.println("Error: file is not a .txt file");
            System.exit(-1);
        }

        try
        {
            BufferedReader reader = new BufferedReader((new FileReader("./files/" + fileName)));
            String line;

            int lineCount = 1;

            /** add each line to system wide content variable **/
            while((line = reader.readLine()) != null)
                if(!line.equals("") && !Util.isCommentedOut(line))
                {
                    line = Util.removeCharacter(line, '\t'); //remove tabs
                    indexedLines.add(new IndexedLine(lineCount, line));
                    lineCount++;
                    //System.out.println(line);
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void destroy()
    {
        indexedLines = null;
        hashedMethods = null;

        //more to be added in the future
    }


    public ArrayList<IndexedLine> getIndexedLines() {
        return indexedLines;
    }
    public void setIndexedLines(ArrayList<IndexedLine> indexedLines) {
        this.indexedLines = indexedLines;
    }

    public static HashMap<String, IndexedMethod> getHashedMethods() {
        return hashedMethods;
    }
    public static void setHashedMethods(HashMap<String, IndexedMethod> hashedMethods) {
        Reader.hashedMethods = hashedMethods;
    }

    public static void addMethod(String name, IndexedMethod method) {
        hashedMethods.put(name, method);
    }
    public static IndexedMethod getMethod(String name) {
        return hashedMethods.get(name);
    }
}
