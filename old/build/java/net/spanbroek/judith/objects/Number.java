package net.spanbroek.judith.objects;

import net.spanbroek.judith.interpreter.Object;

public class Number extends Object {

    private double value;
    
    public Number(Object parent) {
        super(parent);
    }
    
    public Object create(double value) {
        Number result = new Number(this);
        result.value = value;
        return result;
    }
    
    public double getValue() {
        return value;
    }

}
