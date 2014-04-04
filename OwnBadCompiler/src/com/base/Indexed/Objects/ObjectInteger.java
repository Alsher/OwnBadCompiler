package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;


public class ObjectInteger extends IndexedObject {

    private static final String TYPE = "int";

    private int lineNumber;
    private String name;
    private int intValue;
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

    public ObjectInteger(String name, IndexedObject object)
    {
        this.name = name;
        this.lineNumber = object.getLineNumber();

        try { this.intValue = (Integer)object.getValue(); } //this can fail due to object being a string or not value
        catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: "+ getType() + " | Needs to be compiled:" + needsCompiler + " | Name:" + name + " | Value:" + intValue + "]";
    }

    @Override
    public Integer getValue()
    {
        return intValue;
    }

    @Override
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


    public void setNeedsCompiler(boolean needsCompiler)
    {
        this.needsCompiler = needsCompiler;
    }

    public boolean needsCompiler()
    {
        return needsCompiler;
    }
}
