package com.base;

import com.base.Indexed.Actions.ActionOut;
import com.base.Indexed.IndexedAction;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Indexed.Objects.ObjectInteger;
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

    static HashMap<String, IndexedMethod> methods = new HashMap<>();

    public static void compile(HashMap<String, IndexedMethod> inputHash)
    {
        double startTime = System.nanoTime();

        //make inputHash available for other methods
        methods = inputHash;

        /** compile every object that has the needsCompiler flag **/
        for(IndexedMethod method : methods.values())
        {
            for(IndexedObject object : method.getObjects())
                if(object.needsCompiler())
                    compileObject(method, object);
                /*
                    Note: compileObject does not have to return the Object as 'object'
                    functions as a pointer, i.e. the changes done by compileObject automatically recur to the rootMethod
                    Hint: reason why Java is sorta shit
                 */

            for(IndexedAction action : method.getActions())
                if(action.needsCompiler())
                    compileObject(method, action);

        }

        for(IndexedMethod method : methods.values())
        {
            System.out.println("Method:" + method);
            for(IndexedObject object : method.getObjects())
                System.out.println("Object: " + object);
            for(IndexedAction action : method.getActions())
                System.out.println("Action: " + action);
            System.out.println();
        }

        System.out.println("Compiler takes: " + (System.nanoTime() - startTime)/(double)1000000 + "ms.");
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
        }
        else if(object instanceof IndexedAction)
        {
            if(object instanceof ActionOut)
            {
                ActionOut actionOut = (ActionOut) object;
                String[] parameter = actionOut.getParameter();
                for(int i = 0; i < parameter.length; i++) {
                    if (rootMethod.getVariable(parameter[i]) != null && !rootMethod.getVariable(parameter[i]).needsCompiler())
                        parameter[i] = rootMethod.getVariable(parameter[i]).getValue().toString();
                    else if(Util.isAReturnMethod(parameter[i], methods))
                        parameter[i] = methods.get(Util.removeCharacters(parameter[i], '(', ')')).getReturnObject().getValue().toString();
                    else if(parameter[i].startsWith("\"") && parameter[i].endsWith("\""))
                        parameter[i] = Util.removeCharacters(parameter[i], '"');
                    else {
                        System.err.println("Error: " + parameter[i] + "is not a valid parameter!");
                        parameter = new String[]{};
                    }
                }
                actionOut.setParameter(parameter);
                actionOut.setNeedsCompiler(false);
                actionOut.call();
            }
        }
    }
}
