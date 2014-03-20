package com.base.Indexed.Objects;

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
    {
        this.lineNumber = -1;
        this.name = "ERROR";
    }

    @Override
    public String toString()
    {
        return "[Line:" + lineNumber + " | Type: String | Name:" + name + " | Content:" + content + "]";
    }

    @Override
    public String getValue()
    {
        return content;
    }

    @Override
    public String getType()
    {
        return "String";
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
