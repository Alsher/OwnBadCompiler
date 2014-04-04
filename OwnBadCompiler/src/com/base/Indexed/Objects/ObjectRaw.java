package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;

public class ObjectRaw extends IndexedObject{

    private static final String TYPE = "raw";

    private int lineNumber;
    private String rawContent;

    private boolean needsCompiler;

    public ObjectRaw(int lineNumber, String rawContent)
    {
        this.lineNumber = lineNumber;
        this.rawContent = rawContent;
        this.needsCompiler = true;
    }

    public ObjectRaw()
    {
        this.lineNumber = -1;
    }

    @Override
    public String toString() {
        return "[Line:" + lineNumber + " | Type: " + getType() + " | Needs to be compiled:" + needsCompiler + " | raw content:" + rawContent + "]";
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
        return TYPE;
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

    public void setNeedsCompiler(boolean needsCompiler)
    {
        this.needsCompiler = needsCompiler;
    }

    public boolean needsCompiler()
    {
        return needsCompiler;
    }
}
