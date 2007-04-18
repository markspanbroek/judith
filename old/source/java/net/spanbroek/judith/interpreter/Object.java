package net.spanbroek.judith.interpreter;

import java.util.*;

/**
 * Represents a judith object.
 *
 * @author Mark Spanbroek
 */
public class Object {

    /**
     * The parent of this object.
     */
    private Object parent;
    
    /**
     * The class of this object.
     */
    private Class clazz = new Class();
    
    /**
     * A context containing the instance variables.
     */
    private Context objects;
    
    /**
     * Constructs an object with no parent.
     */
    public Object() {
        this.parent = this;
        init();
    }
    
    /**
     * Constructs an object as a child of the specified object.
     */
    protected Object(Object parent) {
        this.parent = parent;
        init();
    }

    /**
     * Initializes the object.
     */    
    protected void init() {
        if (parent != this) {
            objects = parent.objects.deepCopy();
        }
        objects.declare("parent", parent);
        clazz.declare("create", 0, new CreateMethod());
        clazz.declare("equals", 1, new EqualsMethod());
    }
    
    /**
     * Creates a child of this object.
     */
    public Object create() {
        return new Object(this);
    }
    
    /**
     * Returns the context containing the object variables.
     */
    public Context getContext() {
        return context;        
    }
    
    /**
     * Sets the context containing the object variables.
     */
    public void setContext(Context context) {
        this.context = context;
    }
    
    /**
     * Calls the method with the specified name, parameters, self object and
     * caller object, and returns the result of this method.
     *
     * @throws RuntimeException when no method with the specified name and 
     *   parameter count was declared.
     */    
    protected Object call(String name, Object[] parameters, Object caller
      , Object self) {
        
        if (clazz.hasMethod(name, parameters.length)) {
            return clazz.call(name, parameters, caller, self, this);
        }
        else {
            if (parent != this) {
                return parent.call(name, parameters, caller, self);
            }
            else {
                // TODO: throw judith exception
                throw new RuntimeException(
                  "unknown method " + name + "," + parameters.length
                );
            }
        }
        
    }
    
    /**
     * Calls the method with the specified name, parameters and caller object,
     * and returns the result of this method.
     */
    public Object call(String name, Object[] parameters, Object caller) {
        return call(name, parameters, caller, self);
    }

    /**
     * The Object.create method.
     */
    private class CreateMethod implements Method {
        public Object call(Object[] parameters, Object self, Object caller){
            return self.create();
        }
    }
    
    /**
     * The Object.equals method.
     */
    private class EqualsMethod implements Method {
        public Object call(Object[] parameters, Object self, Object caller) {
            if (Object.this == parameters[0]) {
                return Interpreter.getInstance().getBoolean().create(true);
            }
            else {
                return Interpreter.getInstance().getBoolean().create(false);
            }
        }
    }
    
}