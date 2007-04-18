package net.spanbroek.judith.objects;

import net.spanbroek.judith.interpreter.Object;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

/**
 * Represents a judith object whose methods are implemented in Java.
 *
 * @author Mark Spanbroek
 */
public class JavaObject extends Object {

    /**
     * The wrapped java object.
     */
    private java.lang.Object wrapped = this;

    /**
     * Constructs a JavaObject using the specified parent object.
     */
    public JavaObject(Object parent) {
        super(parent);
    }
    
    /**
     * Constructs a JavaObject that is a wrapper around the specified wrapped
     * java object.
     */
    public JavaObject(Object parent, java.lang.Object wrapped) {
        this(parent);
        this.wrapped = wrapped;
    }

    /**
     * Tries to find and call a java method in this object that matches the name 
     * and parameters. If no java method can be found, the regular method for 
     * finding methods is used.
     */    
    protected Object call(String name, Object[] parameters, Object self,
      Object caller) {
      
        // construct the array of parameter types
        Class[] parametertypes = new Class[parameters.length];
        for (int i=0; i<parameters.length; i++) {
            parametertypes[i] = Object.class;
        }
        
        // find the java method through reflection
        Method method;
        try {
            method = wrapped.getClass().getMethod("_"+name, parametertypes);
        }
        catch(NoSuchMethodException exception) {
            // when no java method could be found, try to find a judith method
            // that matches
            return super.call(name, parameters, self, caller);
        }
        
        // check whether the result type is a judith object
        if (!Object.class.isAssignableFrom(method.getReturnType())) {
            // if the result type of the java method is not a judith object, try
            // to find a judith method that matches
            return super.call(name, parameters, self, caller);
        }
        
        // call the method and return the result
        try {
            return (Object)method.invoke(
              wrapped, (java.lang.Object[])parameters
            );
        }
        catch(java.lang.Exception exception) {
            // todo: throw judith exception (maybe cast when it already is one)
            throw new RuntimeException(exception);
        }
        
    }
    
    /**
     * Imports a judith object from the java classpath.
     */
    public Object _import(Object name) {

        try {
        
            // find the class object with the specified name
            // todo: throw judith exception when name is not of type Text
            Class clazz = getClass().forName(((Text)name).getValue());
    
            // check whether this class is a child of the judith Object class
            if (Object.class.isAssignableFrom(clazz)) {
                
                // find the constructor with a sole Object parameter
                Constructor c = clazz.getConstructor(new Class[]{Object.class});
                
                // call the constructor and return the resulting object.
                return (Object)c.newInstance(new java.lang.Object[]{this});
            
            }
            else {
            
                // find the constructor with no parameters
                Constructor c = clazz.getConstructor(new Class[]{});
                
                // call the constructor and return the resulting object inside
                // a wrapper
                return new JavaObject(
                  this, 
                  c.newInstance(new java.lang.Object[]{})
                );
            
            }
            
        }
        catch(java.lang.Exception exception) {
            // todo: throw judith exception (maybe cast when it already is one)
            throw new RuntimeException(exception);
        }
        
    }

}