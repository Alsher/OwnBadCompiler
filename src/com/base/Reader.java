package com.base;


import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/*
    The Reader is the entry point for the whole program as it runs everything right from the constructor.
    The startTime, endTime etc. is for performance testing. The console will output the time in ms.
*/

public class Reader
{
    private static ArrayList<IndexedLine> indexedLines = new ArrayList<>();

    private static HashMap<String, IndexedMethod> hashedMethods = new HashMap<>();

    public static void read(String fileName)
    {
        double value[] = new double[4];

        /** index file **/
        double start = System.nanoTime();
        indexContent(fileName);
        value[0] = (System.nanoTime() - start)/(double)1000000;

        /** hash the file  without any further calculation **/
        start = System.nanoTime();
        hashedMethods = HashSystem.hashMethods(indexedLines);
        value[1] = (System.nanoTime() - start)/(double)1000000;

        /** parse methods in hashedMethods **/
        start = System.nanoTime();
        hashedMethods = ParseSystem.parseMethods(hashedMethods);
        value[2] = (System.nanoTime() - start)/(double)1000000;

        /** compile | run **/
        start = System.nanoTime();
        Compiler.compile(hashedMethods);
        value[3] = (System.nanoTime() - start)/(double)1000000;

        if(Compiler.debugOutput)
        {
            System.out.println("indexing took " + value[0] + "ms.");
            System.out.println("hashing took " + value[1] + "ms.");
            System.out.println("parsing took " + value[2] + "ms.");
            System.out.println("compiling took " + value[3] + "ms.");
            System.out.println("Total: " + (value[0] + value[1] + value[2] + value[3]));
        }
    }

    private static void indexContent(String fileName)
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
            BufferedReader reader;
            if(fileName.startsWith("/"))
                reader = new BufferedReader((new FileReader(fileName)));
            else
                reader = new BufferedReader((new FileReader("./files/" + fileName)));
            String line;

            int lineCount = 1;

            /** add each line to system wide content variable **/
            while((line = reader.readLine()) != null) {
                if (!line.equals("") && !line.startsWith("//")) {
                    line = Util.removeCharacters(line, '\t'); //remove tabs
                    indexedLines.add(new IndexedLine(lineCount, line));
                }
                lineCount++;
            }
            reader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

    }
}
