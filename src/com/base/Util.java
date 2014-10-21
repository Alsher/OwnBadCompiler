package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
    A lot of the Util methods are not necessary, but they are left in for later use.
    Keep in mind to shorten and optimize those methods in every possible way to increase performance.
    Every unnecessary loop has to be prevented.

    The Util method has a loose order: any non-boolean method goes on top, followed by the boolean ones.
    Do pair up methods with the same purpose but different inputs.

    Some Util methods need some work as some of these are somewhat a hack
 */

public class Util {

    public static ArrayList<String> removeFromTo(ArrayList<String> input, int start, int end)
    {
        if(end - start != 1)
            for(int i = start; i <= end; i++) {
                input.remove(start);
            }
        else
            for(int i = start; i < end; i++) {
                input.remove(start);
            }

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
        return null;
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

    public static String removeCharacters(String input, Character... values)
    {
        for(char v : values)
            input = removeCharacter(input, v);
        return input;
    }

    public static String[] removeCharacters(String[] input, Character... values)
    {
        for(int i = 0; i < input.length; i++)
            for(char v : values)
                input[i] = removeCharacter(input[i], v);
        return input;
    }

    public static String[] trimArray(String[] input)
    {
        for(int i = 0; i < input.length; i++)
            input[i] = input[i].trim();

        return input;
    }

    public static String getMarkedString(String[] tokens)
    {
        return getMarkedString(removeCharacter(Arrays.toString(tokens), ','));
    }



    public static String toUsefulString(ArrayList<String> input)
    {
        return Util.removeCharacters(input.toString(), ',');
    }

    public static String toUsefulString(String[] input)
    {
        return Util.removeCharacters(Arrays.toString(input), '[', ']', ',');
    }

    public static String toUsefulString(int[] input)
    {
        return Util.removeCharacters(Arrays.toString(input), '[', ']', ',');
    }

    public static ArrayList<IndexedObject> hashToArray(HashMap<String, IndexedObject> input)
    {
        ArrayList<IndexedObject> returnList = new ArrayList<>();

        for (String key : input.keySet()) {
            returnList.add(input.get(key));
        }
        return returnList;
    }

    public static boolean containsNonMathType(String input)
    {
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(input);
        return m.find();
    }

    public static int getEqualOperator(ArrayList<String> content)
    {
        for(int i = 0; i < content.size(); i++)
            if(content.get(i).contains("="))
                return i;
        return 0;
    }

    public static int getEqualOperator(String content)
    {
        //will only return first equalOperator
        return content.indexOf("=");
    }

    public static int getPosition(String content, char c)
    {
        return content.indexOf(c);
    }

    public static boolean isMethodCall(String input)
    {
        return Compiler.methods.get(Util.removeCharacters(input, '[', '(', ')', ']')) != null;
    }

    public static boolean isAReturnMethod(String possibleCall, HashMap<String, IndexedMethod> methods)
    {
        String modifiedString = Util.removeCharacters(possibleCall, '(', ')');
        return possibleCall.endsWith(")") && methods.get(modifiedString) != null && methods.get(modifiedString).getType() != Compiler.METHOD_TYPE_VOID;
    }

    public static boolean isInteger(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if (!Character.isDigit(input.charAt(i)))
                return false;

        return true;
    }

    public static boolean isInteger(String[] input)
    {
        boolean returnBoolean = false;
        for(String string : input)
            returnBoolean = isInteger(string);

        return returnBoolean;
    }

    public static boolean isInteger(ArrayList<String> input)
    {
        boolean returnBoolean = false;
        for(String string : input)
            returnBoolean = isInteger(string);

        return returnBoolean;
    }

    public static boolean isAMathOperator(String input)
    {
        for (int i = 0; i < input.length(); i++)
            if ((input.charAt(i) == '+' || input.charAt(i) == '-') && input.length() <= 1)
                return true;

        return false;
    }

    public static boolean isInitialization(String input)
    {
        return input.contains("=");
    }
}