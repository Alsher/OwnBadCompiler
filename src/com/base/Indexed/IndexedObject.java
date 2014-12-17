package com.base.Indexed;

/*
    A IndexedObject will be any kind of operation inside a method, such as a new Integer Variable or a if() statement.
    IndexedObject is abstract and therefor an entry point for every single action.
    Every IndexedObject has to override (at least at this point) the toString() method to actually work neatly with a System.out.print[ln]() command.
    Every IndexedObject also has to have a getValue(), a getLineNumber() and a getType() method overwritten;

    The abstract class is used for bundling every object. This allows to easily implement any action into an IndexedStatement without having
    to specify a constructor for every single object.

    The toString() method should return in the following format: [Content1:-content- | Content2:-content- | Content3:-content-]

    Every IndexedObject should have an empty Constructor and a full one, taking in every single parameter.
 */

public abstract class IndexedObject
{
    public static final int VAR_TYPE_RAW = 0;
    public static final int VAR_TYPE_RETURN = 1;
    public static final int VAR_TYPE_INT = 2;
    public static final int VAR_TYPE_STRING = 3;

    public static final int ACTION_TYPE_OUT = 4;

    //possibility to add a parent system right here

    public abstract String toString();
    public abstract Object getValue();
    public abstract void setValue(Object value);
    public abstract int getLineNumber();
    public abstract int getType();
    public abstract String getName();

    public abstract boolean needsCompiler();
}
