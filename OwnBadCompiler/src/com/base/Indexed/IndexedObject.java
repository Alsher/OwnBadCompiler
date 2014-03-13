package com.base.Indexed;

/*
    A IndexedObject will be any kind of operation inside a method, such as a new Integer Variable or a if() statement.
    IndexedObject is abstract and therefor an entry point for every single action.
    Every IndexedObject has to have (at least at this point) a toString() method to actually work neatly with a System.out.print[ln]() command.

    The abstract class is used for bundling every action. This allows to easily implement any action into an IndexedStatement without having
    to specify a constructor for every single action.
 */

public abstract class IndexedObject
{
    //possibility to add a parent system right here

    public abstract String toString();
}
