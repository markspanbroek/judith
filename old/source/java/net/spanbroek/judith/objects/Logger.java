package net.spanbroek.judith.objects;

import net.spanbroek.judith.objects.JavaObject;
import net.spanbroek.judith.interpreter.Object;

/**
 * Represents the judith Logger object.
 */
public class Logger extends JavaObject {

    /**
     * Constructs an exception with the specified parent object.
     */
    public Logger(Object parent) {
        super(parent);
    }

    /**
     * Logs the string representation of the specified object to stdout.
     */
    public Object _info(Object object) {
        // todo: use judith asString method
        System.out.println(object.toString());
        return this;
    }

}