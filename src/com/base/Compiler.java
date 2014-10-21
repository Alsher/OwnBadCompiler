package com.base;

import com.base.Indexed.Actions.ActionOut;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
import com.base.Indexed.Objects.ObjectRaw;
import com.base.Indexed.Objects.ObjectReturn;
import com.base.Indexed.Objects.ObjectString;

import java.util.HashMap;

/*
    The compiler finally compiles every object that needs to be compiled.
    The compiler is the place for if checks to be executed or loops to be done.

    As the compiler needs to be very reliable and fast for reducing possible
    lag by using it, it _has_ to be lightweight and optimized.

    A function to compile specific methods will be added.

    For example one method inside this language hooks into a Game Mechanic.
    This method therefor needs to be compiled every time the normal Game Mechanic
    method would be called.
    This plan needs further optimization as it _will_ produce ridiculous performance issues
    with the current system.

    The compiler is _not even close_ to be finished
 */

public class Compiler {

    public static final int VAR_TYPE_RAW = 0;
    public static final int VAR_TYPE_RETURN = 1;
    public static final int VAR_TYPE_INT = 2;
    public static final int VAR_TYPE_STRING = 3;
    public static final int ACTION_TYPE_OUT = 4;

    public static final int METHOD_TYPE_VOID = 0;
    public static final int METHOD_TYPE_INT = 1;
    public static final int METHOD_TYPE_STRING = 2;

    static HashMap<String, IndexedMethod> methods = new HashMap<>();

    static boolean debugOutput = true;

    public static void compile(HashMap<String, IndexedMethod> inputHash)
    {
        //make inputHash available for other methods
        methods = inputHash;

        IndexedMethod mainMethod = methods.get("main");
        if(mainMethod == null)
        {
            System.err.println("Error: No main method found");
            System.exit(-1);
        }

        /** compile every object in main method that has the needsCompiler flag **/
        for(IndexedObject object : mainMethod.getObjects())
            if(object.needsCompiler())
                compileObject(mainMethod, object);
            /*
                Note: compileObject does not have to return the Object as the variable 'object'
                works as a pointer, i.e. the changes done by compileObject automatically recur to the rootMethod
                Hint: reason why Javas inaccuracy is sorta shit
             */

        if(debugOutput) {
            for (IndexedMethod method : methods.values()) {
                System.out.println("Method:" + method);
                for (IndexedObject object : method.getObjects())
                    System.out.println("Object: " + object);
                System.out.println();
            }
        }
    }

    public static void compile(IndexedMethod method)
    {
        for(IndexedObject object : method.getObjects())
            if(object.needsCompiler())
                compileObject(method, object);
    }

    private static void compileObject(IndexedMethod rootMethod, Object object)
    {
        if(object instanceof IndexedObject)
        {
            if(object instanceof ObjectInteger)
            {
                ObjectInteger objectInteger = (ObjectInteger) object;
                objectInteger.setIntValue(MathSystem.calculate(rootMethod, objectInteger.getStringValue(), true));
                objectInteger.setNeedsCompiler(false);
            }
            else if(object instanceof ObjectString)
            {
                ObjectString objectString = (ObjectString) object;
                objectString.setContent(StringSystem.getContent(rootMethod, objectString.getContent()));
                objectString.setNeedsCompiler(false);
            }
            else if(object instanceof ObjectReturn)
            {
                ObjectReturn objectReturn = (ObjectReturn) object;
                objectReturn.setReturnObject(rootMethod.getVariable(Util.removeCharacter(objectReturn.getContent().split(" ")[1], ';')));
                objectReturn.setNeedsCompiler(false);
            }
            else if(object instanceof ActionOut)
            {
                ActionOut actionOut = (ActionOut) object;
                String[] parameter = actionOut.getParameter();
                for(int i = 0; i < parameter.length; i++) {
                    if (rootMethod.getVariable(parameter[i]) != null && !rootMethod.getVariable(parameter[i]).needsCompiler())
                        parameter[i] = rootMethod.getVariable(parameter[i]).getValue().toString();
                    else if(Util.isAReturnMethod(parameter[i], methods))
                        parameter[i] = methods.get(Util.removeCharacters(parameter[i], '(', ')')).getReturnObject().getValue().toString();
                    else if(parameter[i].startsWith("\"") && parameter[i].endsWith("\""))
                        parameter[i] = parameter[i].substring(1, parameter[i].length() - 1);
                    else {
                        System.err.println("Error: " + parameter[i] + " is not a valid parameter!");
                        parameter = new String[]{"ERROR"};
                    }
                }
                actionOut.setParameter(parameter);
                actionOut.setNeedsCompiler(false);
                actionOut.call();
            }

            else if(object instanceof ObjectRaw)
            {
                ObjectRaw objectRaw = (ObjectRaw) object;
                if(objectRaw.getAdditionalInfo() == VAR_TYPE_RAW)
                {
                    int equapOpPos = Util.getEqualOperator(objectRaw.getRawContent());
                    IndexedObject uObject =  rootMethod.getVariable(objectRaw.getRawContent().substring(0, equapOpPos - 1));
                    if(uObject != null)
                    {
                        switch (uObject.getType())
                        {
                            case VAR_TYPE_INT:
                                ObjectInteger objectInteger = (ObjectInteger)uObject;
                                objectInteger.setIntValue(MathSystem.calculate(rootMethod, objectRaw.getRawContent().substring(equapOpPos + 1), true));
                                break;

                            case VAR_TYPE_STRING:
                                ObjectString objectString = (ObjectString)uObject;
                                objectString.setContent(StringSystem.getContent(rootMethod, objectRaw.getRawContent().substring(equapOpPos + 1)));
                                break;
                        }
                    }
                }
            }
        }
    }
}