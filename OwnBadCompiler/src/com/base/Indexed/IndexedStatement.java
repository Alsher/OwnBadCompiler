package com.base.Indexed;

public class IndexedStatement {

    private IndexedObject indexedObject;

    public IndexedStatement(IndexedObject indexedObject)
    {
        this.indexedObject = indexedObject;
    }

    public IndexedStatement()
    {
        indexedObject = null;
    }

    @Override
    public String toString()
    {
        if(indexedObject != null)
            return "[Object:" + indexedObject + "]";
        return "Error";
    }

    public IndexedObject getIndexedObject() {
        return indexedObject;
    }

    public void setIndexedObject(IndexedObject indexedObject) {
        this.indexedObject = indexedObject;
    }
}
