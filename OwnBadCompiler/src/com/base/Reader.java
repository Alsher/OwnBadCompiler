package com.base;


import com.base.Indexed.IndexedLine;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedStatement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    private ArrayList<IndexedMethod> indexedMethods = new ArrayList<>();

    public Reader(String fileName)
    {
        startFinal = System.nanoTime();

        /** index file **/
        startTime = System.nanoTime();
        indexContent(fileName);
        endTime = System.nanoTime();

        stepFinal[0] = endTime - startTime;


        /** index method headers and content **/
        startTime = System.nanoTime();
        indexedMethods = Parse.parseMethods(indexedLines);
        endTime = System.nanoTime();

        stepFinal[1] = endTime - startTime;

        /** check for variable mistakes etc **/
        //TODO: actually add this


        startTime = System.nanoTime();
        for(IndexedMethod indexd : indexedMethods)
        {
            System.out.println("Indexed methods are: " + indexd);
            for(IndexedStatement statement : indexd.getStatements())
                System.out.println("Indexed statements are: " + statement);
            System.out.println();
        }
        endTime = System.nanoTime();

        stepFinal[9] = endTime - startTime;
        endFinal = System.nanoTime();

        System.out.println();
        System.out.println("Number of lines: " + indexedLines.size());
        System.out.println();

        System.out.println("The indexing of all lines took " + (stepFinal[0] / (double)1000000) + " ms.");
        System.out.println("The indexing of all methods took " + (stepFinal[1] / (double)1000000) + " ms.");
        System.out.println("The output took " + (stepFinal[9] / (double)1000000) + "ms.");
        System.out.println();

        System.out.println("The whole operation took " + ((endFinal - startFinal) / (double)1000000) + " ms.");

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
                if(!line.equals(""))
                {
                    indexedLines.add(new IndexedLine(lineCount, line));
                    lineCount++;
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
        indexedMethods = null;

        //more to be added in the future
    }


    public ArrayList<IndexedLine> getIndexedLines() {
        return indexedLines;
    }
    public void setIndexedLines(ArrayList<IndexedLine> indexedLines) {
        this.indexedLines = indexedLines;
    }
    public ArrayList<IndexedMethod> getIndexedMethods() {
        return indexedMethods;
    }
    public void setIndexedMethods(ArrayList<IndexedMethod> indexedMethods) {
        this.indexedMethods = indexedMethods;
    }
}
