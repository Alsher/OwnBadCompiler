package com.base.Indexed.Actions;

import com.base.Indexed.IndexedObject;
import com.base.Util;

public class ActionOut extends IndexedObject
{
    private int lineNumber;
    private String[] parameter;
    private boolean needsCompiler;

    public ActionOut(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public ActionOut()
    {
        needsCompiler = true;
    }

    public void call()
    {

        System.out.print("Action called: \"");
        for(String s : getParameter())
            System.out.print(s);
        System.out.print("\"" + (char)10);
    }

    public String toString() {
        return "[Line: " + lineNumber + " | parameters: " + Util.toUsefulString(parameter) + "]";
    }

    public String getName()
    {
        return "out";
    }

    public Object getValue() {
        return parameter;
    }
    public void setValue(Object object)
    {
        if(object instanceof String)
            parameter = new String[]{(String)object};
        else
            System.err.println("Error: " + object + " is not a String");
    }

    public String[] getParameter() {
        return parameter;
    }
    public void setParameter(String[] parameter)
    {
        this.parameter = parameter;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public int getType() {
        return ACTION_TYPE_OUT;
    }

    public boolean needsCompiler()
    {
        return needsCompiler;
    }
    public void setNeedsCompiler(boolean needsCompiler)
    {
        this.needsCompiler = needsCompiler;
    }
}
