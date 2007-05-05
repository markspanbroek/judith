package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

public class Parent extends Object {

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