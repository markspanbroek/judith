package net.spanbroek.judith.interpreter;

import java.util.*;

/**
 * Represents a class of objects.
 */
public class Class {

    /**
     * The context in which this class was declared.
     */
    private Context context = new Context();

    /**
     * A mapping between method names and methods.
     */
    private Map methods = new HashMap();

    /**
     * Declares a new method with the specified name and parameter count.
     */    
    public void declare(String name, int parameters, Method method) {
        methods.put(name + "," + parameters, method);
    }
    
    /**
     * Returns whether or not a method with the specified name and number of
     * parameters was declared in this class.
     */
    public boolean hasMethod(String name, int parameters) {
        return methods.containsKey(name + "," + parameters);
    }
    
    /**
     * Calls the method with the specified name, parameters, self object, caller
     * object and instance object, and returns the result of this method.
     */
    public Object call(String name, Object[] parameters, Object caller
      , Object self, Object instance) {
      
        Method method = (Method)methods.get(name + "," + parameters.length);
        return method.call(parameters, caller, self, instance, context);
      
    }

}