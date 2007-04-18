package net.spanbroek.judith.objects;

import net.spanbroek.judith.interpreter.Interpreter;
import net.spanbroek.judith.interpreter.Object;
import net.spanbroek.judith.objects.JavaObject;

/**
 * Represents the judith Exception object.
 *
 * @author Mark Spanbroek
 */
public class Exception extends java.lang.RuntimeException {

    /**
     * Throws the exception
     */    
    public Object _throw() {
        throw this;
    }

}