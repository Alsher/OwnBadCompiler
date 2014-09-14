package com.base.Indexed.Methods;

import com.base.Indexed.IndexedAction;
import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;
import com.base.Util;

import java.util.ArrayList;
import java.util.HashMap;


public class MethodInteger extends IndexedMethod {

    private static final String TYPE = "int";

    private int headerLineNumber;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;
    private HashMap<String, IndexedObject> variables;
    private ArrayList<IndexedAction> actions;
    private IndexedObject returnObject;

    public MethodInteger(int headerLineNumber, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

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
        /** set return object **/
        for(IndexedObject object : objects)
            if(object.getType().equals("return"))
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

    public ArrayList<IndexedAction> getActions() {
        return actions;
    }

    public void setActions(ArrayList<IndexedAction> actions) {
        this.actions = actions;
    }
    public void addAction(IndexedAction action) {
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

    public String getType()
    {
        return TYPE;
    }

    public IndexedObject getReturnObject()
    {
        return returnObject;
    }
    public void setReturnObject(IndexedObject returnObject)
    {
        this.returnObject = returnObject;
    }

    public ArrayList<IndexedObject> getCombinedObjects()
    {
        ArrayList<IndexedObject> combinedObjects = objects;

        //add the variables to ArrayList
        combinedObjects.addAll(Util.hashToArray(variables));

        //check for returnObject and add it to the ArrayList
        if (returnObject != null)
            combinedObjects.add(returnObject);

        //sort combinedObjects by line number (see Util class)
        combinedObjects = Util.toSortedArray(combinedObjects);

        return combinedObjects;
    }
}
