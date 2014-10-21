package com.base.Indexed;

import java.util.ArrayList;
import java.util.HashMap;

/*
    A IndexedMethod will be any kind of method, both including a returning one and a void one.
    Every IndexedMethod must have the getters and setters listed below.

    A call() method provides a way to actively call and compile a method (eg. set return Value)
 */

public abstract class IndexedMethod {

    public HashMap<String, IndexedObject> variables;
    public ArrayList<IndexedObject> actions;

    public abstract void call();

    public abstract String toString();

    public abstract void setHeaderLineNumber(int lineNumber);
    public abstract int getHeaderLineNumber();

    public abstract Integer getBraceStart();
    public abstract void setBraceStart(Integer braceStart);
    public abstract Integer getBraceEnd();
    public abstract void setBraceEnd(Integer braceEnd);

    public abstract ArrayList<IndexedObject> getObjects();
    public abstract HashMap<String, IndexedObject> getVariables();

    public abstract IndexedObject getObjectAt(int index);
    public abstract IndexedObject getVariable(String name);

    public abstract void setObjects(ArrayList<IndexedObject> objects);
    public abstract void addObject(IndexedObject object);

    public abstract void setVariables(HashMap<String, IndexedObject> variables);
    public abstract void addVariable(String name, IndexedObject variable);

    public abstract ArrayList<IndexedObject> getActions();

    public abstract void setActions(ArrayList<IndexedObject> actions);
    public abstract void addAction(IndexedObject action);

    public abstract HashMap<String, IndexedObject> getParameter();
    public abstract void setParameter(HashMap<String, IndexedObject> parameter);
    public abstract boolean hasParameter();

    public abstract int getType();
    public abstract String getName();

    public abstract void setReturnObject(IndexedObject returnObject);
    public abstract IndexedObject getReturnObject();
}

