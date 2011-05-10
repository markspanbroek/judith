package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Error;

/**
 * A container for methods that can be shared among objects. It is possible
 * to register a replacement object for this class. This means that all
 * instances of this class should replace (parts of) themselves by the
 * replacement object.
 *
 * @author Mark Spanbroek
 */
class ReplaceableClass extends BasicClass {

    /**
     * The replacement object will be used to replace (parts of) all
     * instances of this class. Equals <code>null</code> when there is no
     * replacement object.
     */
    private Object replacement = null;

    /**
     * Returns whether or not a replacement object has been registered for
     * this class.
     */
    boolean hasReplacement() {
        return (replacement != null);
    }

    /**
     * Returns the replacement object that will be used to replace
     * (parts of) all instances of this class. Equals <code>null</code> when
     * there is no replacement object.
     */
    Object getReplacement() {
        return replacement;
    }

    /**
     * Registers a copy of the specified object as the replacement object
     * for all instances of this class.
     */
    void setReplacement(Object object) {
        if (replacement != null) {
            throw new Error("A class can only be replaced once.");
        }
        replacement = object.copy();
    }
}