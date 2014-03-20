package com.base.Indexed.Objects;


import com.base.Indexed.IndexedObject;

public class ObjectReturn extends IndexedObject{

    private int lineNumber;
    private IndexedObject returnObject;


    public ObjectReturn(int lineNumber, IndexedObject returnObject)
    {
        this.lineNumber = lineNumber;
        this.returnObject = returnObject;
    }

    public ObjectReturn()
    {
        this.lineNumber = -1;
    }

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: return | Return Object:" + returnObject + " | Returns value:" + getValue() + "]";
    }

    @Override
    public Object getValue()
    {
        if(returnObject != null)
            return returnObject.getValue();
        return "Error: returnObject is not set";
    }

    @Override
    public String getType()
    {
        return "return";
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public IndexedObject getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(IndexedObject returnObject) {
        this.returnObject = returnObject;
    }
}
