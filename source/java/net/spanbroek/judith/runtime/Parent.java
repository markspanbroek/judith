package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

/**
 * Wraps an object, and forwards all method calls to the parent object.
 *
 * @author Mark Spanbroek 
 */
class Parent extends Object {

    /**
     * The wrapped object.
     */
    private Object wrapped;

    /**
     * Creates a parent object wrapper for the specified object.
     */
    public Parent(Object wrapped) {
        super((ObjectCore)null);
        this.wrapped = wrapped;
    }

    /**
     * Returns the current core of the wrapped object. 
     */
    ObjectCore getCurrentCore() {
        return wrapped.getCurrentCore();
    }

    /**
     * Sets the core of the wrapped object.
     */
    void setCore(ObjectCore core) {
        wrapped.setCore(core);
    }

    /**
     * Calls the specified method of the wrapped object using the caller
     * of the method as the 'self' value, when the caller is a descendant of the
     * wrapped object. If the caller is not a descendant of the wrapped object,
     * an Exception is thrown.
     */
    protected Object call(String name, Object[] parameters,
      Object self, Object caller) {
        if (caller.isDescendantOf(self)) {
            return wrapped.call(name, parameters, caller, caller);
        }
        else {
            throw new Exception(
              "only descendants are allowed to call the methods of " +
              "a parent object"
            );
        }
    }
    
    public Object copy() {
        throw new Exception(
          "parent objects can not be copied, or derived from"
        );
    }

}
