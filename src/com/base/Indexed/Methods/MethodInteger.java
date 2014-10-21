package com.base.Indexed.Methods;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MethodInteger extends IndexedMethod {

    private int headerLineNumber;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;
    private HashMap<String, IndexedObject> variables;

    private HashMap<String, IndexedObject> parameter;
    private boolean hasParameter;

    private ArrayList<IndexedObject> actions;
    private IndexedObject returnObject;

    public MethodInteger(int headerLineNumber, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

        hasParameter = false;
        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    public MethodInteger(int headerLineNumber, String name, HashMap<String, IndexedObject> parameter)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

        this.parameter = parameter;
        hasParameter = true;

        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    public MethodInteger()
    {
        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    @Override
    public void call()
    {
        com.base.Compiler.compile(this);
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
        addObject(action);
    }

    public void setVariables(HashMap<String, IndexedObject> variables) {
        this.variables = variables;
    }
    public void addVariable(String name, IndexedObject variable) {
        variables.put(name, variable);
    }

    public HashMap<String, IndexedObject> getParameter() {
        return parameter;
    }
    public void setParameter(HashMap<String, IndexedObject> parameter) {
        this.parameter = parameter;
    }
    public boolean hasParameter()
    {
        return hasParameter;
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
        return com.base.Compiler.METHOD_TYPE_INT;
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
