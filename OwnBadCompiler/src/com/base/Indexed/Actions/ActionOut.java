package com.base.Indexed.Actions;

import com.base.Indexed.IndexedAction;
import com.base.Util;

public class ActionOut extends IndexedAction
{
    private static final String TYPE = "action";

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
        System.out.println("Action called: " + Util.toUsefulString(getParameter()));
    }

    public String toString() {
        return "[Line: " + lineNumber + " | parameters: " + Util.toUsefulString(parameter) + "]";
    }

    public String[] getParameter() {
        return parameter;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getType() {
        return TYPE;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public void setParameter(String[] parameter)
    {
        this.parameter = parameter;
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
