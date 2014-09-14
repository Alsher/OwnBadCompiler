package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;


public class ObjectInteger extends IndexedObject {

    private static final String TYPE = "int";

    private int lineNumber;
    private String name;
    private int intValue;
    private String stringValue; //needed for storing uncompiled values
    private boolean needsCompiler;

    public ObjectInteger(int line, String name, int intValue)
    {
        this.lineNumber = line;
        this.name = name;
        this.intValue = intValue;
    }

    public ObjectInteger(ObjectInteger objectInteger)
    {
        this.lineNumber = objectInteger.getLineNumber();
        this.name = objectInteger.getName();
        this.intValue = objectInteger.getIntValue();
    }

    public ObjectInteger()
    {
        this.lineNumber = -1;
        this.name = "ERROR";
        this.needsCompiler = true;
    }

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: "+ getType() + " | Needs to be compiled:" + needsCompiler + " | Name:" + name
                + (stringValue == null ? (" | Value:" + intValue) : (" | Value:" + intValue + " | StringValue: " + stringValue)) + "]";
    }

    public Integer getValue()
    {
        return getIntValue();
    }

    public String getType()
    {
        return TYPE;
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
    public int getIntValue() {
        return intValue;
    }
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getStringValue()
    {
        return stringValue;
    }
    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public void setNeedsCompiler(boolean needsCompiler)
    {
        this.needsCompiler = needsCompiler;
    }

    public boolean needsCompiler()
    {
        return needsCompiler;
    }
}
