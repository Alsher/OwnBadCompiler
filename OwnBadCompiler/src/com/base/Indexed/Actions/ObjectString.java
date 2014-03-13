package com.base.Indexed.Actions;

import com.base.Indexed.IndexedObject;


public class ObjectString extends IndexedObject {

    private int lineNumber;
    private String name;
    private String content;

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
    {}

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Name:" + name + " | Content:" + content + "]";
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
}
