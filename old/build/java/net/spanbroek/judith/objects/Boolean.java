package net.spanbroek.judith.objects;

import net.spanbroek.judith.interpreter.Object;
import net.spanbroek.judith.interpreter.Method;

public class Boolean extends Object {

    private boolean value;

    public Boolean(Object parent) {
        super(parent);
        declare("not", 0, new NotMethod());
    }
        
    public Object create(boolean value) {
        Boolean result = new Boolean(this);
        result.value = value;
        return result;
    }
    
    public Object create() {
        return create(value);
    }
    
    public boolean getValue() {
        return value;
    }
    
    private class NotMethod implements Method {
        public Object call(Object[] parameters, Object self, Object caller) {
            return create(!value);
        }
    }

}
