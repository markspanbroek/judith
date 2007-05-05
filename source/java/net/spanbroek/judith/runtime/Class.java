package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Method;
import net.spanbroek.judith.Exception;
import net.spanbroek.judith.Error;
import java.util.*;

/**
 * A container for methods that can be shared among objects. It is possible
 * to register a replacement object for this class. This means that all
 * instances of this class should replace (parts of) themselves by the
 * replacement object.
 *
 * @author Mark Spanbroek
 */
class Class {

    /**
     * A map containing the methods. The key under which a method is stored
     * is comprised of the method name, followed by a comma, followed by the
     * number of arguments that the method takes. For example: "setFoo,1".
     */
    protected Map<String,Method> methods = new HashMap<String,Method>();

    /**
     * The replacement object will be used to replace (parts of) all
     * instances of this class. Equals <code>null</code> when there is no
     * replacement object.
     */
    private Object replacement = null;

    /**
     * Adds a method to this class using the specified name.
     */
    public void declare(String name, Method method) {
        String identifier = name + "," + method.getParameterCount();
        methods.put(identifier, method);
    }

    /**
     * Return the method of the specified name and number of parameters.
     * Returns <code>null</code> when the method could not be found.
     */
    public Method getMethod(String name, int parameterCount) {
        String identifier = name + "," + parameterCount;
        return methods.get(identifier);
    }

    /**
     * Returns whether or not a replacement object has been registered for
     * this class.
     */
    public boolean hasReplacement() {
        return (replacement != null);
    }

    /**
     * Returns the replacement object that will be used to replace
     * (parts of) all instances of this class. Equals <code>null</code> when
     * there is no replacement object.
     */
    public Object getReplacement() {
        return replacement;
    }

    /**
     * Registers a copy of the specified object as the replacement object
     * for all instances of this class.
     */
    public void setReplacement(Object object) {
        if (replacement != null) {
            throw new Error("A class can only be replaced once.");
        }
        replacement = object.copy();
    }

}