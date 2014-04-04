package com.base;

import com.base.Indexed.IndexedMethod;

import java.util.HashMap;

//not finished

public class ErrorSystem {


    public static int checkForErrors(HashMap<String, IndexedMethod> parsedMethods)
    {
        //TODO: add error checks

        return 1;
    }

    public static void outputError(String error, int line)
    {
        System.err.println("Error in line " + line + " with following description: " + error);

    }

}
