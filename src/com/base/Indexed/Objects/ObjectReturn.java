package com.base.Indexed.Objects;


import com.base.Indexed.IndexedObject;

public class ObjectReturn extends IndexedObject{

    private int lineNumber;
    private IndexedObject returnObject;
    private boolean needsCompiler;
    private String content;

    public ObjectReturn(int lineNumber, IndexedObject returnObject)
    {
        this.lineNumber = lineNumber;
        this.returnObject = returnObject;
        this.needsCompiler = true;
    }

    public ObjectReturn()
    {
        this.lineNumber = -1;
    }

    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: return | Needs to be compiled:" + needsCompiler +  "| Return Object:" + returnObject + " | Returns value:" + getValue() + "]";
    }

    public Object getValue() {
        if(returnObject != null)
            return returnObject.getValue();
        return content;
    }

    public String getName()
    {
        return returnObject.getName();
    }

    public void setValue(Object object) {
        if(object instanceof IndexedObject)
            this.returnObject = (IndexedObject) object;
        else
            System.err.println("Error: " + object + " is not an IndexedObject");
    }

    public int getType()
    {
        return com.base.Compiler.VAR_TYPE_RETURN;
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

    public void setContent(String content)
    {
        this.content = content;
    }
    public String getContent()
    {
        return content;
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
