package com.base.Indexed.Actions;

import com.base.Indexed.IndexedObject;


public class ObjectInteger extends IndexedObject {

    private int lineNumber;
    private String name;
    private int value;

    public ObjectInteger(int line, String name, int value)
    {
        this.lineNumber = line;
        this.name = name;
        this.value = value;
    }

    public ObjectInteger(ObjectInteger objectInteger)
    {
        this.lineNumber = objectInteger.getLineNumber();
        this.name = objectInteger.getName();
        this.value = objectInteger.getValue();
    }

    public ObjectInteger()
    {}

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Name:" + name + " | Value:" + value + "]";
    }

    public String getName() {
        return name;
    }
    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {this.lineNumber = lineNumber;}
    public void setName(String name) {
        this.name = name;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
