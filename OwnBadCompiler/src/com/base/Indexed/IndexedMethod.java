package com.base.Indexed;

//will be overhauled

import java.util.ArrayList;
import java.util.HashMap;

public abstract class IndexedMethod {

    //public abstract void call();

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
    public abstract IndexedObject getVariablesWithKey(String name);

    public abstract void setObjects(ArrayList<IndexedObject> objects);
    public abstract void addObject(IndexedObject object);

    public abstract void setVariables(HashMap<String, IndexedObject> variables);
    public abstract void setVariable(String name, IndexedObject variable);

    public abstract String getType();
    public abstract String getName();

    public abstract IndexedObject getReturnObject();

}

