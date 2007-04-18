package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Method;
import net.spanbroek.judith.Error;
import java.util.*;

public class Class {

    /**
     * A map containing the methods. The key under which a method is stored
     * is comprised of the method name, followed by a comma, followed by the 
     * number of arguments that the method takes. For example: "setFoo,1".
     */
    protected Map<String,Method> methods = new HashMap<String,Method>();


    protected Object replacement = null;

    /**
     * Adds a method to this class using the specified name.
     */
    public void declare(String name, Method method) {

        // Add method to method map.
        String identifier = name + "," + method.getParameterCount();
        methods.put(identifier, method);

    }


    public Method getMethod(String name, int parameterCount) {

        // construct method identifier using name and number of parameters
        String identifier = name + "," + parameterCount;

        // retreive the method object
        return methods.get(identifier);

    }

    public void replace(Object replacement) {

        if (this.replacement == null) {
            this.replacement = replacement;
        }
        else {
            throw new Error("Class already has a replacement.");
        }

    }


}