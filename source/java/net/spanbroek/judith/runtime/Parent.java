package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

public class Parent extends Object {

    public Parent(Object child) {

        super(child.state.parent.state);

    }

    protected Object call(String name, Object[] parameters,
      Object self, Object caller) {

        if (caller.isDescendantOf(self)) {
            return super.call(name, parameters, caller, caller);
        }
        else {
            throw new Exception(
              "parent methods may only be called by a descendant"
            );
        }

    }

}