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
    private ObjectCore core;

    /**
     * Constructs a new judith object using the specified parent object and 
     * the scope in which the object was created.
     */
    public Object(Object parent, Scope scope) {
        this(scope);
        core.setParent(parent.call("copy"));
    }

    /**
     * Constructs a new judith object using the specified scope in which the 
     * object was created. The created object will be at the top of the 
     * inheritance chain.
     */
    public Object(Scope scope) {
        this(new ObjectCore());
        core.setScope(new Scope(scope));
    }

    /**
     * Constructs a new judith object using the specified object core.
     */
    Object(ObjectCore core) {
        this.core = core;
    }

    /**
     * Returns the core of this object. Resolves replacements before returning
     * the core.
     */
    synchronized ObjectCore getCore() {
        while (getCurrentCore().hasReplacement()) {
            ObjectCore oldCore = getCurrentCore();
            setCore(getCurrentCore().getReplacement());
            oldCore.setReplaced();
        }
        return getCurrentCore();
    }

    /**
     * Returns the core of this object without first resolving replacements.
     */
    ObjectCore getCurrentCore() {
        return core;
    }

    /**
     * Sets the core of this object to the specified core.
     */
    void setCore(ObjectCore core) {
        this.core = core;
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
        return call(name, parameters, this, caller);
    }

    /**
     * Handles a method call. The self object is the object whose method was 
     * called. This is not necessarily equal to the current object, because
     * a call could have been forwarded from a child object.
     *
     * @throws Exception when the method could not be found.
     */
    protected Object call(String name, Object[] parameters,
      Object self, Object caller) {

        ObjectCore core = getCore();

        // Retrieve method from the class
        Method method = core.getClazz().getMethod(name, parameters.length);

        // check whether the method was found
        if (method != null) {
            Scope scope = createMethodScope(self, caller);

            // execute the method
            return method.execute(parameters, self, caller, scope);

        }
        else {

            // forward the call to the parent object, if it exists.
            if (core.hasParent()) {

                return core.getParent().call(name, parameters, self, caller);

            }
            else {

                // create reader-friendly description of the method
                String description = name;
                if (parameters.length > 0) {
                    description += "(";
                    for (int i=0; i<parameters.length; i++) {
                        if (i>0) {
                            description += ", ";
                        }
                        description += "object";
                        if (parameters.length > 1) {
                            description += i;
                        }
                    }
                    description += ")";
                }

                // throw exception describing the method that was not found.
                throw new Exception("unknown method: " + description);

            }

        }

    }

    protected Scope createMethodScope(Object self, Object caller) {
        // create a new child scope of the object scope, containing the
        // 'self', 'caller' and 'result' objects
        Scope scope = new Scope(getCore().getScope());
        return scope;
    }

    /**
     * Returns the native Java object that is associated with this judith
     * object, or with one of its ancestors.
     *
     * @return the native Java object, or <code>null</code> when no native Java
     *   object was associated with this judith object, or with one of its
     *   ancestors.
     */
    public java.lang.Object getNativeObject() {
        ObjectCore core = getCore();
        if (core.hasNativeObject()) {
            return core.getNativeObject();
        }
        else {
            if (core.hasParent()) {
                return core.getParent().getNativeObject();
            }
            else {
                return null;
            }
        }
    }

    /**
     * Associates the specified native Java object with this judith object.
     */
    public void setNativeObject(java.lang.Object object) {
        getCore().setNativeObject(object);
    }

    /**
     * Determines whether or not this object is compatible with the specified 
     * object. For each object 'A' holds that it is compatible with object 'B',
     * when 'A' is of the same class as 'B', or if the parent of 'A' is
     * compatible with 'B'.
     */
    public boolean isCompatibleWith(Object object) {
        ObjectCore core = getCore();
        if (core.getClazz() == object.getCore().getClazz()) {
            return true;
        }
        else {
            if (core.hasParent()) {
                return core.getParent().isCompatibleWith(object);
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
        ObjectCore core = getCore();
        if (core.hasParent()) {
            if (core.getParent().equals(object)) {
                return true;
            }
            else {
                return core.getParent().isDescendantOf(object);
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

}
