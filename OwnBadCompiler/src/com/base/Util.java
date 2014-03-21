package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.Methods.MethodInteger;
import com.base.Indexed.Methods.MethodString;
import com.base.Indexed.Methods.MethodVoid;

import java.util.ArrayList;
import java.util.Arrays;


/*
    A lot of the Util methods are not necessary, but they are left in for later use.
    Keep in mind to shorten and optimize those methods in every possible way to increase performance.
    Every unnecessary loop has to be prevented.

    The Util method has a loose order: any non-boolean method goes on top, followed by the boolean ones.
    Do pair up methods with the same purpose but different inputs.
 */

public class Util {

    public static String removeBrackets(String input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');
        return input;
    }

    public static String[] removeBrackets(String[] input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');
        return input;
    }

    public static ArrayList<String> removeBrackets(ArrayList<String> input)
    {
        input = Util.removeCharacter(input, '(');
        input = Util.removeCharacter(input, ')');

        return input;
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
        return array;
    }

    public static String removeCharacter(String input, Character removeChar)
    {
        String result = "";
        for(Character c : input.toCharArray())
            if(!c.equals(removeChar))
                result += c;
        return result;
    }

    public static String[] removeCharacter(String input[], Character removeChar)
    {
        for(int i = 0; i < input.length; i++)
            input[i] = removeCharacter(input[i], removeChar);
        return input;
    }

    public static ArrayList<String> removeCharacter(ArrayList<String> input, Character removeChar)
    {
        for(int i = 0; i < input.size(); i++)
            input.set(i, removeCharacter(input.get(i), removeChar));
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
        {
            return line.substring(markStart, markEnd - 1);
        }
        return "ERROR: getMarkedString FAILED";
    }

    public static String getMarkedString(String[] tokens)
    {
        return getMarkedString(removeCharacter(Arrays.toString(tokens), ','));
    }

    public static IndexedMethod getMethod(String[] tokens, int line)
    {
        switch(tokens[1])
        {
            case "void": return new MethodVoid(line, Util.removeCharacter(tokens[2], ':'));
            case "int" : return new MethodInteger(line, Util.removeCharacter(tokens[2], ':'));
            case "String" : return new MethodString(line, Util.removeCharacter(tokens[2], ':'));
            default: return null;
        }
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

    public static boolean isCommentedOut(String line)
    {
        return line.length() > 1 && line.substring(0, 2).equals("//");
    }
}
