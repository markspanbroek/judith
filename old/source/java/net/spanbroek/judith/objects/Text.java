package net.spanbroek.judith.objects;

import net.spanbroek.judith.interpreter.Object;

public class Text extends Object {

    private String value;
    
    public Text(Object parent) {
        super(parent);
    }
    
    public Object create(String value) {
        Text result = new Text(this);
        result.value = value;
        return result;
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString() {
        return value;
    }
    
}
