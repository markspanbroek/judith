package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

/**
 * Wraps an object 
 */
class Parent extends Object {

    private Object wrapped;

    public Parent(Object wrapped) {
        super((ObjectCore)null);
        this.wrapped = wrapped;
    }

    ObjectCore getCurrentCore() {
        return wrapped.getCurrentCore();
    }

    void setCore(ObjectCore core) {
        wrapped.setCore(core);
    }

    /**
     * Calls the specified method of the wrapped object using the caller
     * of the method as the 'self' value, when the caller is a descendant of the
     * wrapped object. If the caller is not a descendant of the wrapped object,
     * the call is forwarded to the 
     */
    protected Object call(String name, Object[] parameters,
      Object self, Object caller) {
        if (caller.isDescendantOf(self)) {
            return wrapped.call(name, parameters, caller, caller);
        }
        else {
            return wrapped.call(name, parameters, self, caller);
        }
    }

}
