package com.base.Indexed;

import com.base.Util;

import java.util.ArrayList;
import java.util.HashMap;

//will be overhauled

public class IndexedMethod {

    private int headerLineNumber;
    private String type;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;
    private HashMap<String, IndexedObject> variables;
    private IndexedObject returnObject;

    public IndexedMethod(Integer headerLineNumber, String type, String name, int braceStart, int braceEnd, ArrayList<IndexedObject> objects, HashMap<String, IndexedObject> variables, IndexedObject returnObject)
    {
        this.headerLineNumber = headerLineNumber;
        this.type = type;
        this.name = name;
        this.braceStart = braceStart;
        this.braceEnd = braceEnd;
        this.objects = objects;
        this.variables = variables;
        this.returnObject = returnObject;
    }

    public IndexedMethod(Integer headerLineNumber, String type, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.type = type;
        this.name = name;
    }

    public IndexedMethod()
    {
        type = null;
        name = null;
        objects = null;
    }

    @Override
    public String toString()
    {
        return "[Head line:" + headerLineNumber + " Start line:" + braceStart + " | End line:" + braceEnd + " | Method type:" + type + " | Name: " + Util.removeMethodIndicator(name) + " | Has content: " + (braceStart != null && braceEnd != null) + " | Return object: " + returnObject + "]";
    }


    public Integer getHeaderLineNumber() {
        return headerLineNumber;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name.substring(0, name.length() - 1);
    }
    public void setName(String name) {
        this.name = name + ":";
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

    public ArrayList<IndexedObject> getObject() {
        return objects;
    }
    public void setObject(ArrayList<IndexedObject> objects) {
        this.objects = objects;
    }

    public void addObject(IndexedObject object) {
        objects.add(object);
    }
    public void addObjectAtPosition(IndexedObject object, int index) {
        objects.add(index, object);
    }

    public void setObjectAtPosition(IndexedObject object, int index) {
        objects.set(index, object);
    }

    public HashMap<String, IndexedObject> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, IndexedObject> variables) {
        this.variables = variables;
    }

    public void addVariable(String name, IndexedObject variable)
    {
        variables.put(name, variable);
    }

    public IndexedObject getVariable(String name)
    {
        return variables.get(name);
    }

    public IndexedObject getReturnObject() {
        return returnObject;
    }
    public void setReturnObject(IndexedObject returnObject) {
        this.returnObject = returnObject;
    }
}
