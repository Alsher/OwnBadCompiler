package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;

public class ObjectRaw extends IndexedObject{

    private int lineNumber;
    private String rawContent;

    public ObjectRaw(int lineNumber, String rawContent)
    {
        this.lineNumber = lineNumber;
        this.rawContent = rawContent;
    }

    public ObjectRaw()
    {
        this.lineNumber = -1;
    }

    @Override
    public String toString() {
        return "[Line number:" + lineNumber + " | raw content:" + rawContent + "]";
    }

    @Override
    public Object getValue() {
        return rawContent;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getType()
    {
        return "raw";
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getRawContent() {
        return rawContent;
    }

    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }
}
