package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;

public class ObjectString extends IndexedObject {

    private static final String TYPE = "String";

    private int lineNumber;
    private String name;
    private String content;
    private boolean needsCompiler;

    public ObjectString(int lineNumber, String name, String content, boolean needsCompiler)
    {
        this.lineNumber = lineNumber;
        this.name = name;
        this.content = content;
        this.needsCompiler = needsCompiler;
    }

    public ObjectString(int lineNumber, String name, String content)
    {
        this.lineNumber = lineNumber;
        this.name = name;
        this.content = content;
    }

    public ObjectString(ObjectString objectString)
    {
        this.lineNumber = objectString.getLineNumber();
        this.name = objectString.getName();
        this.content = objectString.getContent();
    }

    public ObjectString()
    {
        this.lineNumber = -1;
        this.name = "ERROR";
        this.needsCompiler = true;
    }

    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: " + getType() + " | Needs to be compiled:" + needsCompiler + " | Name:" + name + " | Content:" + content + "]";
    }

    public String getValue()
    {
        return content;
    }

    public String getType()
    {
        return TYPE;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {this.lineNumber = lineNumber;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
