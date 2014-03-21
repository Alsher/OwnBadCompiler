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
    double startTime, endTime, startFinal, endFinal;
    double[] stepFinal = new double[10];

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
        //TODO

        /** compile | run **/
        //TODO

        /** output what we parsed so far **/
        List<String> keys = new ArrayList<>(hashedMethods.keySet());
        for (String key : keys)
        {
            System.out.println("Indexed methods are: " + hashedMethods.get(key));
            System.out.println("Indexed variables are: " + hashedMethods.get(key).getVariables());
            System.out.println("Indexed objects are: " + hashedMethods.get(key).getObjects());
            System.out.println();

        }
//
//        startFinal = System.nanoTime();
//
//        /** index file **/
//        startTime = System.nanoTime();
//        indexContent(fileName);
//        endTime = System.nanoTime();
//
//        stepFinal[0] = endTime - startTime;
//
//
//        /** index method headers and content **/
//        startTime = System.nanoTime();
//        hashedMethods = ParseSystem.parseMethods(indexedLines);
//        endTime = System.nanoTime();
//
//        stepFinal[1] = endTime - startTime;
//
//
//        startTime = System.nanoTime();
//
//        List<String> keys = new ArrayList<>(hashedMethods.keySet());
//        for (String key : keys)
//        {
//                System.out.println("Indexed methods are: " + hashedMethods.get(key));
//                System.out.println("Indexed variables are: " + hashedMethods.get(key).getVariables());
//                System.out.println("Indexed objects are: " + hashedMethods.get(key).getObjects());
//                System.out.println();
//        }
//
//        endTime = System.nanoTime();
//
//        stepFinal[9] = endTime - startTime;
//        endFinal = System.nanoTime();
//
//        System.out.println();
//        System.out.println("Number of lines: " + indexedLines.size());
//        System.out.println();
//
//        System.out.println("The indexing of all lines took " + (stepFinal[0] / (double)1000000) + " ms.");
//        System.out.println("The indexing of all methods took " + (stepFinal[1] / (double)1000000) + " ms.");
//        System.out.println("The output took " + (stepFinal[9] / (double)1000000) + "ms.");
//        System.out.println();
//
//        System.out.println("The whole operation took " + ((endFinal - startFinal) / (double)1000000) + " ms.");
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
