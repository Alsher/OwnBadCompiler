package com.base;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Phil on 07.09.14.
 */
public class StringSystem
{

    public static String getContent(IndexedMethod rootMethod, String content)
    {
        ArrayList<String> components = new ArrayList<>(Arrays.asList(content.split(" ")));

        int equalOpPos = Util.getEqualOperator(components);
        String equalop = components.get(equalOpPos);
//        System.out.println("SS: " + components + equalop);

        components = Util.removeFromTo(components, 0, equalOpPos);

        HashMap<String, IndexedObject> variables = rootMethod.getVariables();

        components = Util.removeCharacter(components, ';');

        String[] parsedComponents = new String[components.size()];

        //iterate through component list
        for(int i = 0; i < components.size(); i++)
        {
            String component = components.get(i);

            /** check if component is variable **/
            if(variables.get(component) != null &&
               variables.get(component) instanceof ObjectString)
                parsedComponents[i] = ((ObjectString) variables.get(component)).getValue();

            /** check if component is valid method call **/
            if(Util.isMethodCall(component))
                parsedComponents[i] = Compiler.methods.get(Util.removeCharacters(component, '[', '(', ')', ']')).getReturnObject().getValue().toString();
        }

        return Util.toUsefullString(parsedComponents);
    }
}
