package com.base.Indexed;

import com.base.Util;

import java.util.ArrayList;

public class IndexedMethod {

    private int headerLineNumber;
    private String type;
    private String name;
    private Integer braceStart, braceEnd;
    private ArrayList<IndexedStatement> statements;
    private IndexedStatement returnStatement;

    public IndexedMethod(Integer headerLineNumber, String type, String name, int braceStart, int braceEnd, ArrayList<IndexedStatement> statements, IndexedStatement returnStatement)
    {
        this.headerLineNumber = headerLineNumber;
        this.type = type;
        this.name = name;
        this.braceStart = braceStart;
        this.braceEnd = braceEnd;
        this.statements = statements;
        this.returnStatement = returnStatement;
    }

    public IndexedMethod(Integer headerLineNumber, String type, String name)
    {
        this.headerLineNumber = headerLineNumber;
        this.type = type;
        this.name = name;
    }

    public IndexedMethod()
    {
        type = null;
        name = null;
        statements = null;
    }

    @Override
    public String toString()
    {
        return "[Head line:" + headerLineNumber + " Start line:" + braceStart + " | End line:" + braceEnd + " | Method type:" + type + " | Name: " + Util.removeMethodIndicator(name) + " | Has content: " + (braceStart != null && braceEnd != null) + " | Return statement: " + "not yet implemented" + "]";
    }


    public Integer getHeaderLineNumber() {
        return headerLineNumber;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name.substring(0, name.length() - 2);
    }
    public void setName(String name) {
        this.name = name + "()";
    }

    public Integer getBraceStart() {
        return braceStart;
    }
    public void setBraceStart(Integer braceStart) {
        this.braceStart = braceStart;
    }

    public Integer getBraceEnd() {
        return braceEnd;
    }
    public void setBraceEnd(Integer braceEnd) {
        this.braceEnd = braceEnd;
    }

    public ArrayList<IndexedStatement> getStatements() {
        return statements;
    }
    public void setStatements(ArrayList<IndexedStatement> statements) {
        this.statements = statements;
    }

    public void addStatement(IndexedStatement statement) {
        statements.add(statement);
    }
    public void addStatementAtPosition(IndexedStatement statement, int index) {
        statements.add(index, statement);
    }

    public void setStatementAtPosition(IndexedStatement statement, int index) {
        statements.set(index, statement);
    }

    public IndexedStatement getReturnStatement() {
        return returnStatement;
    }
    public void setReturnStatement(IndexedStatement returnStatement) {
        this.returnStatement = returnStatement;
    }
}
