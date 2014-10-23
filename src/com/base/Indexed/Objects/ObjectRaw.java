package com.base.Indexed.Objects;

import com.base.Indexed.IndexedObject;

public class ObjectRaw extends IndexedObject {

    private int lineNumber;
    private String rawContent;
    private int additionalInfo;

    private boolean needsCompiler;

    public ObjectRaw(int lineNumber, String rawContent) {
        this.lineNumber = lineNumber;
        this.rawContent = rawContent;
        this.needsCompiler = true;
        additionalInfo = 0;
    }

    public ObjectRaw() {
        this.lineNumber = -1;
    }

    @Override
    public String toString() {
        return "[Line:" + lineNumber + " | Type: " + getType() + " | Needs to be compiled:" + needsCompiler + " | Additional Information: " + additionalInfo + " | raw content:" + rawContent + "]";
    }

    public Object getValue() {
        return getRawContent();
    }
    public void setValue(Object content)
    {
        if(content instanceof String)
            this.rawContent = (String)content;
        else
            System.err.println("Error: " + content + " is not a String");
    }

    public String getName()
    {
        return rawContent;
    }

    public int getLineNumber() {
        return lineNumber;
    }
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getType() {
        return com.base.Compiler.VAR_TYPE_RAW;
    }

    public String getRawContent() {
        return rawContent;
    }
    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    public int getAdditionalInfo()
    {
        return additionalInfo;
    }
    public void setAdditionalInfo(int additionalInfo)
    {
        this.additionalInfo = additionalInfo;
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
