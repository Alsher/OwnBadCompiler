package com.base.Indexed.Methods;

import com.base.Indexed.IndexedMethod;
import com.base.Indexed.IndexedObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodVoid extends IndexedMethod {

    private int headerLineNumber;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedObject> objects;

    private ArrayList<IndexedObject> parameter;
    private boolean hasParameter;

    private ArrayList<IndexedObject> actions;
    private HashMap<String, IndexedObject> variables;

    public MethodVoid(int headerLineNumber, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

        hasParameter = false;

        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    public MethodVoid(int headerLineNumber, String name, ArrayList<IndexedObject> parameter)
    {
        this.headerLineNumber = headerLineNumber;
        this.name = name;

        this.parameter = parameter;
        hasParameter = true;

        objects = new ArrayList<>();
        actions = new ArrayList<>();
        variables = new HashMap<>();
    }

    public MethodVoid()
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
        return "[Head line:" + getHeaderLineNumber() + " | Start line:" + braceStart + " | End line:" + braceEnd + " | Method type: " + getType() +
               " | Name: " + name + " | Has content: " + (braceStart != null && braceEnd != null) + "]";
    }

    /** objects **/
    public void setObjects(ArrayList<IndexedObject> objects) {
        this.objects = objects;
    }
    public ArrayList<IndexedObject> getObjects() {
        return objects;
    }
    public IndexedObject getObject(int index) {
        return objects.get(index);
    }
    public void addObject(IndexedObject object) {
        objects.add(object);
    }

    /** variables **/
    public void setVariables(HashMap<String, IndexedObject> variables) {
        this.variables = variables;
    }
    public HashMap<String, IndexedObject> getVariables() {
        return variables;
    }
    public IndexedObject getVariable(String name) {
        return variables.get(name);
    }
    public void addVariable(String name, IndexedObject variable) {
        variables.put(name, variable);
    }

    /** parameters **/
    public void setParameters(ArrayList<IndexedObject> parameter) {
        this.parameter = parameter;
    }
    public ArrayList<IndexedObject> getParameters() {
        return parameter;
    }
    public IndexedObject getParameter(int index)
    {
        return parameter.get(index);
    }
    public void addParameter(IndexedObject parameter)
    {
        this.parameter.add(parameter);
    }
    public boolean hasParameter()
    {
        return hasParameter;
    }

    /** actions **/
    public void setActions(ArrayList<IndexedObject> actions) {
        this.actions = actions;
    }
    public ArrayList<IndexedObject> getActions() {
        return actions;
    }
    public IndexedObject getAction(int index)
    {
        return this.actions.get(index);
    }
    public void addAction(IndexedObject action) {
        this.actions.add(action);
        addObject(action);
    }

    /** returnObject **/
    public IndexedObject getReturnObject()
    {
        return null;
    }
    public void setReturnObject(IndexedObject returnObject) {

    }

    /** lineNumber **/
    public void setHeaderLineNumber(int lineNumber) {
        headerLineNumber = lineNumber;
    }
    public int getHeaderLineNumber() {
        return headerLineNumber;
    }

    /** name **/
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /** braces **/
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

    /** type **/
    public int getType()
    {
        return com.base.Compiler.METHOD_TYPE_VOID;
    }
}
