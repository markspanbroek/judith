package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a variable scope, in which names are associated with objects.
 * Scopes can be nested, so that a variable that is not present in the current
 * scope will be searched in the parent scope.
 *
 * @author Mark Spanbroek
 */
public class Scope {

    /**
     * The parent scope. When a variable is not present in the current Scope,
     * the parent scope is searched.
     */
    protected Scope parent;

    /**
     * The name-object map.
     */
    private Map<String,Object> bindings;

    /**
     * Creates a new scope that is not nested inside a parent scope.
     */
    public Scope() {

        this.parent = null;
        this.bindings = new HashMap<String,Object>();

    }

    /**
     * Creates a new scope that is nested inside the specified parent scope.
     */
    public Scope(Scope parent) {

        this.parent = parent;
        this.bindings = new HashMap<String,Object>();

    }

    /**
     * Creates a new scope that is nested inside the specified parent scope, and
     * that contains the name-object bindings from the specified map.
     */
    private Scope(Scope parent, Map<String,Object> map) {

        this.parent = parent;
        this.bindings = map;

    }

    /**
     * Returns whether an object is associated with the specified name.
     */
    public boolean contains(String name) {

        if (bindings.containsKey(name)) {
            return true;
        }

        if (parent == null) {
            return false;
        }

        return parent.contains(name);
    }

    /**
     * Returns the object that is currently associated with the specified name.
     *
     * @throws Exception when there is no object associated with the specified
     *   name.
     */
    public Object get(String name) {

        Object result = bindings.get(name);
        if (result == null) {
            if (parent != null) {
                result = parent.get(name);
            }
            else {
                throw new Exception("unknown object: " + name);
            }
        }
        return result;

    }

    /**
     * Assigns the specified object to the specified name. This name should have
     * been declared previously.
     *
     * @throws Exception when the specified name hasn't been declared
     *   before.
     * @see #declare(String, Object)
     */
    public void set(String name, Object object) {

        if (bindings.containsKey(name)) {
            bindings.put(name, object);
        }
        else {
            if (parent != null) {
                parent.set(name, object);
            }
            else {
                throw new Exception("unknown object: " + name);
            }
        }

    }

    /**
     * Declares a new name and assigns the specified object to that name.
     */
    public void declare(String name, Object object) {

        bindings.put(name, object);

    }

    /**
     * Makes a copy of this scope. The copy references the same parent scope as
     * the original.
     */
    public Scope copy() {

        return new Scope(
          parent,
          new HashMap<String,Object>(bindings)
        );

    }

}
