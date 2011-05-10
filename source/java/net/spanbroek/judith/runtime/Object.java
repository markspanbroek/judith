package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Exception;

/**
 * Represents a judith object.
 *
 * @author Mark Spanbroek
 */
public class Object {

    /**
     * The core of this object, which contains the instance variables.
     */
    private ReplaceableObject replaceable;

    /**
     * Constructs a new judith object using the specified parent object and 
     * the scope in which the object was created.
     */
    public Object(Object parent, Scope scope) {
        this(scope);
        replaceable.setParent(parent.call("copy"));
    }

    /**
     * Constructs a new judith object using the specified scope in which the 
     * object was created. The created object will be at the top of the 
     * inheritance chain.
     */
    public Object(Scope scope) {
        this(new ReplaceableObject());
        replaceable.setScope(new Scope(scope));
    }

    /**
     * Constructs a new judith object using the specified object core.
     */
    Object(ReplaceableObject core) {
        this.replaceable = core;
    }

    /**
     * Returns the core of this object. Resolves replacements before returning
     * the core.
     */
    synchronized ReplaceableObject getCore() {
        resolveReplacements();
        return getCurrentCore();
    }

    /**
     * Returns the core of this object without first resolving replacements.
     */
    ReplaceableObject getCurrentCore() {
        return replaceable;
    }

    /**
     * Sets the core of this object to the specified core.
     */
    void setCore(ReplaceableObject core) {
        this.replaceable = core;
    }

    /**
     * Adds an inner object to this object.
     */
    public void declare(String name, Object object) {
        getCore().getScope().declare(name, object);
    }

    /**
     * Adds a method to this object using the specified name.
     */
    public void declare(String name, Method method) {
        getCore().getClazz().declare(name, method);
    }

    /**
     * Convenience method for calling judith methods from Java.
     * Executes <code>call(name, parameters, this)</code>.
     */
    public Object call(String name, Object... parameters) {
        return call(name, parameters, this);
    }

    /**
     * Calls the method with the specified name, using the specified parameters
     * and caller object. The caller object is the object that called the 
     * method.
     *
     * @throws Exception when the method could not be found.
     */
    public Object call(String name, Object[] parameters, Object caller) {
        return call(new MethodCall(this, name, parameters, caller));
    }

    protected Object call(MethodCall methodCall) {
        resolveReplacements();
        return replaceable.call(methodCall);
    }

    public java.lang.Object getNativeObject() {
        resolveReplacements();
        return replaceable.getNativeObject();
    }

    /**
     * Associates the specified native Java object with this judith object.
     */
    public void setNativeObject(java.lang.Object object) {
        resolveReplacements();
        replaceable.setNativeObject(object);
    }

    /**
     * Determines whether or not this object is compatible with the specified 
     * object. For each object 'A' holds that it is compatible with object 'B',
     * when 'A' is of the same class as 'B', or if the parent of 'A' is
     * compatible with 'B'.
     */
    public boolean isCompatibleWith(Object object) {
        resolveReplacements();
        if (replaceable.getClazz() == object.getCore().getClazz()) {
            return true;
        }
        else {
            if (replaceable.hasParent()) {
                return replaceable.getParent().isCompatibleWith(object);
            }
            else {
                return false;
            }
        }
    }

    /**
     * Returns whether the specified object can be found in the ancestry of this
     * object.
     */
    boolean isDescendantOf(Object object) {
        resolveReplacements();
        if (replaceable.hasParent()) {
            if (replaceable.getParent().equals(object)) {
                return true;
            }
            else {
                return replaceable.getParent().isDescendantOf(object);
            }
        }
        else {
            return false;
        }
    }

    /**
     * Tests for equality.
     */
    public boolean equals(Object object) {
        return getCore() == object.getCore();
    }

    /**
     * Creates a copy of this object. Its inner scope is copied, but the outer
     * scope and methods are shared between all copies.
     */
    public Object copy() {
        return new Object(getCore().copy());
    }

    /**
     * Registers the specified object as a replacement for this object.
     */
    public synchronized void replace(Object object) {
        getCore().getClazz().setReplacement(object);
    }

    private void resolveReplacements() {
        while (getCurrentCore().hasReplacement()) {
            ReplaceableObject oldCore = getCurrentCore();
            setCore(getCurrentCore().getReplacement());
            oldCore.setReplaced();
        }
    }
}
