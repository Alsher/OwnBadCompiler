package com.base;

import com.base.Indexed.IndexedMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*
    A lot of the Util methods are not necessary, but they are left in for later use.
    Keep in mind to shorten and optimize those methods in every possible way to increase performance.
    Every unnecessary loop has to be prevented.

    The Util method has a loose order: any non-boolean method goes on top, followed by the boolean ones.
    Do pair up methods with the same purpose but different inputs.
 */

public class Util {

    public static String removeSemicolon(String input)
    {
        String result = "";
        for(Character c : input.toCharArray())
            if(!c.equals(';'))
                result += c;

        return result;
    }

    public static ArrayList<String> removeSemicolon(ArrayList<String> input)
    {
        ArrayList<String> returnList = new ArrayList<>();

        for(String string : input)
        {
            String result = "";
            for(Character c : string.toCharArray())
                if(!c.equals(';'))
                    result += c;
            returnList.add(result);
        }
        return returnList;
    }

    public static List<String> removeSemicolon(List<String> input)
    {
        ArrayList<String> returnList = new ArrayList<>();

        for(String string : input)
        {
            String result = "";
            for(Character c : string.toCharArray())
                if(!c.equals(';'))
                    result += c;
            returnList.add(result);
        }
        return returnList;
    }

    public static String removeBrackets(String input)
    {
        String result = "";
        for(Character c : input.toCharArray())
            if(!c.equals('(') && !c.equals(')'))
                result += c;

        return result;
    }

    public static ArrayList<String> removeBrackets(ArrayList<String> input)
    {
        ArrayList<String> returnList = new ArrayList<>();

        for(String string : input)
        {
            String result = "";
            for(Character c : string.toCharArray())
                if(!c.equals('(') && !c.equals(')'))
                    result += c;
            returnList.add(result);
        }
        return returnList;
    }

    public static String removeMethodIndicator(String input)
    {
        String result = "";
        for(Character c : input.toCharArray())
            if(!c.equals(':'))
                result += c;

        return result;
    }

    public static String[] removeEmptyStrings(String[] input)
    {
        ArrayList<String> preResult = new ArrayList<>();

        for(String string : input)
            if(!string.equals(""))
                preResult.add(string);

        return new String[]{preResult.toString()};
    }

    public static ArrayList<String> removeFromTo(ArrayList<String> input, int start, int end)
    {
        for(int i = start; i <= end; i++)
            input.remove(start);

        return input;
    }

    public static String[] removeFromTo(String[] input, int start, int end)
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(input));
        removeFromTo(list, start, end);
        String[] array = new String[list.size()];
        list.toArray(array);

        System.out.println(Arrays.toString(input) + " | " + Arrays.toString(array));
        return array;
    }

    public static String getMarkedString(String line)
    {
        int markStart = 0, markEnd = 0, counter = 1;

        for(Character c : line.toCharArray())
        {
            if(c.equals('"') && markStart == 0)
                markStart = counter;
            else if(c.equals('"') && markStart != 0)
                markEnd = counter;
            counter++;
        }

        if(counter >= line.length())
            return line.substring(markStart, markEnd - 1);


        return "ERROR: getMarkedString FAILED";
    }

    public static boolean isCompleteStatement(String[] tokens)
    {
        boolean isComplete = false;

        for(String string : tokens)
            for(Character c : string.toCharArray())
                if(c.equals(';'))
                    isComplete = true;

        return isComplete;
    }

    public static boolean isInteger(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if (!Character.isDigit(input.charAt(i)))
                return false;

        return true;
    }

    public static boolean isAMathOperator(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if ((input.charAt(i) == '+' || input.charAt(i) == '-') && input.length() <= 1)
                return true;

        return false;
    }

    public static boolean isMethodType(String token)
    {
        return token.equals("void") || token.equals("int") || token.equals("ObjectString");
    }

    public static boolean isMethod(String token)
    {
        return token.length() > 2 && token.substring(token.length() - 2).equals("()");
    }

    public static boolean isInMethod(ArrayList<IndexedMethod> methods, String name)
    {
        for(IndexedMethod method : methods)
            if(method.getName().equals(name))
                return true;
        return false;
    }

    public static boolean isCommentedOut(String line)
    {
        return line.length() > 1 && line.substring(0, 2).equals("//");
    }
}
