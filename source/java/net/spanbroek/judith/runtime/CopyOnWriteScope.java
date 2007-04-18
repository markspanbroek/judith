package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

public class CopyOnWriteScope extends Scope {

    public CopyOnWriteScope(Scope parent) {
        super(parent);
    }

    public void set(String name, Object object) {
        if (map.containsKey(name)) {
            map.put(name, object);
        }
        else {
            if (parent.contains(name)) {
                map.put(name, object);
            }
            else {
                throw new Exception("unknown object: " + name);
            }
        }
    }

}