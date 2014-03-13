package com.base;

import com.base.Indexed.IndexedMethod;

import java.util.ArrayList;
import java.util.List;


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
            {
                System.out.println(input);
                return true;
            }
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
        {
            System.out.println(method.getName() + "/" + name);
            if(method.getName().equals(name))
                return true;
        }
        return false;

    }
}
