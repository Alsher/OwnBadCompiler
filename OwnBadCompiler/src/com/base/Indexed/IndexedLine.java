package com.base.Indexed;

/**
 * Created by Phil on 28.02.14.
 */
public class IndexedLine {

    private String line;
    private int lineNumber;

    public IndexedLine(int lineNumber, String line)
    {
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public IndexedLine()
    {
        this.line = null;
    }

    @Override
    public String toString()
    {
        return "[Line number:" + lineNumber + " | Line content:" + line + "]";
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
