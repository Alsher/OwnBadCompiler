package com.base.Indexed.Methods;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodString extends IndexedMethod {

    private int headerLineNumber;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;
    private HashMap<String, IndexedObject> variables;
    private ArrayList<IndexedObject> actions;
    private IndexedObject returnObject;

    public MethodString(int headerLineNumber, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    public MethodString()
    {
        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    @Override
    public void call()
    {
        //set return object
        for(IndexedObject object : objects)
            if(object.getType() == com.base.Compiler.VAR_TYPE_RETURN)
                setReturnObject(object);
    }

    public String toString() {
        return "[Head line:" + getHeaderLineNumber() + " | Start line:" + braceStart + " | End line:" + braceEnd + " | Method type:" + getType() +
               " | Name: " + name + " | Has content: " + (braceStart != null && braceEnd != null) + " | returns: " + (returnObject != null ? returnObject.getValue() : "null") + "]";
    }

    public void setHeaderLineNumber(int lineNumber) {
        headerLineNumber = lineNumber;
    }
    public int getHeaderLineNumber() {
        return headerLineNumber;
    }

    public ArrayList<IndexedObject> getObjects() {
        return objects;
    }
    public IndexedObject getVariable(String name) {
        return variables.get(name);
    }

    public HashMap<String, IndexedObject> getVariables() {
        return variables;
    }
    public IndexedObject getObjectAt(int index) {
        return objects.get(index);
    }

    public void setObjects(ArrayList<IndexedObject> objects) {
        this.objects = objects;
    }
    public void addObject(IndexedObject object) {
        objects.add(object);
    }

    public ArrayList<IndexedObject> getActions() {
        return actions;
    }

    public void setActions(ArrayList<IndexedObject> actions) {
        this.actions = actions;
    }
    public void addAction(IndexedObject action) {
        this.actions.add(action);
    }

    public void setVariables(HashMap<String, IndexedObject> variables) {
        this.variables = variables;
    }
    public void addVariable(String name, IndexedObject variable) {
        variables.put(name, variable);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Integer getBraceStart() {
        return braceStart;
    }
    public void setBraceStart(Integer braceStart) {
        this.braceStart = braceStart;
    }

    public Integer getBraceEnd() {
        return braceEnd;
    }
    public void setBraceEnd(Integer braceEnd) {
        this.braceEnd = braceEnd;
    }

    public int getType()
    {
        return com.base.Compiler.METHDO_TYPE_STRING;
    }

    public IndexedObject getReturnObject()
    {
        return returnObject;
    }
    public void setReturnObject(IndexedObject returnObject)
    {
        this.returnObject = returnObject;
    }
}
