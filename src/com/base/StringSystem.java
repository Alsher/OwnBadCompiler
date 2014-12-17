package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class StringSystem
{

    public static String getContent(IndexedMethod rootMethod, String content)
    {
        ArrayList<String> components = new ArrayList<>(Arrays.asList(smartSplit(content)));

        int equalOpPos = Util.getEqualOperator(components);
//        String equalop = components.get(equalOpPos);
//        System.out.println("SS: " + components + equalop);

        components = Util.removeFromTo(components, 0, equalOpPos);

        HashMap<String, IndexedObject> variables = rootMethod.getVariables();

        HashMap<String, IndexedObject> parameters = new HashMap<>();

        if(rootMethod.hasParameter())
            for(IndexedObject parameter : rootMethod.getParameters())
                parameters.put(parameter.getName(), parameter);


        components = Util.removeCharacter(components, ';');

        String[] parsedComponents = new String[components.size()];

        //iterate through component list
        for(int i = 0; i < components.size(); i++)
        {
            String component = components.get(i);

            if(component.startsWith("\"") && component.endsWith("\""))
                parsedComponents[i] = component.substring(1, component.length() - 1);

            /** check if component is variable **/
            else if (variables.get(component) != null &&
                    variables.get(component) instanceof ObjectString)
                parsedComponents[i] = ((ObjectString) variables.get(component)).getValue();

            /** check if component is parameter **/

            else if (parameters.get(component) != null &&
                    parameters.get(component) instanceof ObjectString)
                parsedComponents[i] = ((ObjectString) parameters.get(component)).getValue();

            /** check if component is valid method call **/
            else if (Util.isMethodCall(component)) {
                String methodName = component.substring(0, Util.getPosition(component, '('));
                IndexedMethod method = Compiler.methods.get(methodName);

                if (method != null) {
                    if (method.hasParameter()) {
                        String paramString = component.substring(Util.getPosition(component, '(') + 1, Util.getPosition(component, ')'));
                        String[] parameterInCall = Util.trimArray(paramString.split(","));
                        int paramArrayCount = 0;
                        for (String s : parameterInCall) {
                            if (s.startsWith("\"") && s.endsWith("\""))
                                method.getParameter(paramArrayCount).setValue(s.substring(1, s.length() - 1));
                            else
                                method.getParameter(paramArrayCount).setValue(s);
                            paramArrayCount++;
                        }
                    }
                    method.call();
                    parsedComponents[i] = method.getReturnObject().getValue().toString();
                } else
                    parsedComponents[i] = null;
            }
        }
        return calculationEngine(parsedComponents);
    }

    private static String calculationEngine(String[] input)
    {
        String s = "";
        for (String anInput : input) {
            if (anInput != null)
                s += anInput;
        }
        return s;
    }

    public static String[] smartSplit(String input) {
        ArrayList<String> sList = new ArrayList<>();

        int space = -1;
        for (int i = 0; i < input.length(); i++)
        {
            /** check for quotation marks **/
            if(input.charAt(i) == '\"')
            {
                int start = i, end = -1;
                for (int j = i + 1; j < input.length(); j++) {
                    if (input.charAt(j) == '\"') {
                        end = j;
                        break;
                    }
                }
                if(end != -1) {
                    end++;
                    sList.add(input.substring(start, end));
                    i = end;
                    space = end;
                }
            }
            /** check for brackets and skip them **/
            else if(input.charAt(i) == '(')
            {
                for (int j = i + 1; j < input.length(); j++) {
                    if (input.charAt(j) == ')') {
                        i = j + 1;
                        break;
                    }
                }
            }
            /** check for space character **/
            else if(input.charAt(i) == ' ') {
                if(space == -1)
                    sList.add(input.substring(0, i));
                else
                    sList.add(input.substring(space + 1, i));
                space = i;
            }
        }
        /** add last piece in case spaces have been detected and not yet been added **/
        sList.add(input.substring(space == -1 ? 0 : (space + 1)));

        return sList.toArray(new String[sList.size()]);
    }
}
