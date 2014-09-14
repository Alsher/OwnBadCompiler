package com.base.Indexed;

/*
    Every Action has the following syntax: action 'type' 'parameter[]'
 */

public abstract class IndexedAction
{
    public abstract String toString();
    public abstract int getLineNumber();
    public abstract String getType();

    public abstract boolean needsCompiler();

    public abstract void call();
}
