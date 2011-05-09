package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Class;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Error;

/**
 * Contains all instance variables of an object. These have been separated from
 * the {@link Object} class to enable replacement of cores.
 *
 * @author Mark Spanbroek
 */
class ObjectCore implements Cloneable {

    /**
     * The class of the object. Contains the methods of this object.
     */
    private Class clazz = new Class();

    /**
     * The parent object. When a method is called which is not present in the
     * current object, then the method call is forwarded to the parent. The
     * top of the inheritance chain is marked by setting the parent equal to
     * null.
     */
    private Object parent = null;

    /**
     * The scope of the object, which contains the inner objects, and has a
     * reference to the outer objects. Inner objects are those objects which are
     * declared within the object, whereas outer objects are declared outside
     * the object declaration. For example:
     * <pre>
     *   object outer := "This is an outer object of the foo object"
     *   object foo |[
     *      object inner := "This is an inner object of the foo object"
     *   ]|
     * </pre>
     */
    private Scope scope;

    /**
     * A native Java object that is associated with this judith object.
     */
    private java.lang.Object aboriginal = null;

    /**
     * Indicates whether or not this object core has been replaced or not.
     */
    private boolean replaced = false;

    /**
     * Returns the class of the object.
     */
    public Class getClazz() {
        return clazz;
    }

    /**
     * Sets the class of the object.
     */
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    /**
     * Returns whether this object has a parent object.
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Returns the parent object of this object.
     */
    public Object getParent() {
        return parent;
    }

    /**
     * Sets the parent object of this object.
     */
    public void setParent(Object parent) {
        this.parent = parent;
        getScope().declare("parent", new Parent(parent));
    }

    /**
     * Returns the scope of this object.
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the scope of this object.
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Returns whether a native Java object has been associated with this
     * judith object.
     */
    public boolean hasNativeObject() {
        return aboriginal != null;
    }

    /**
     * Returns the native Java object that is associated with this judith
     * object.
     */
    public java.lang.Object getNativeObject() {
        return aboriginal;
    }

    /**
     * Sets the native Java object that is associated with this judith object.
     */
    public void setNativeObject(java.lang.Object object) {
        aboriginal = object;
    }

    public Method getMethod(MethodCall methodCall) {
        final String name = methodCall.getName();
        final Object[] parameters = methodCall.getParameters();
        return getClazz().getMethod(name, parameters.length);
    }

    /**
     * Creates a copy of the object core. The inner scope is copied, but the
     * outer scope and class are shared between all copies.
     */
    public ObjectCore copy() {
        ObjectCore result = clone();
        result.setScope(getScope().copy());
        if (hasParent()) {
            result.setParent(getParent().copy());
        }
        return result;
    }

    /**
     * @see java.lang.Object.clone()
     */
    public ObjectCore clone() {
        try {
            return (ObjectCore)super.clone();
        }
        catch(CloneNotSupportedException exception) {
            throw new Error(exception);
        }
    }

    /**
     * Returns whether or not a replacement is available for this object core.
     */
    public boolean hasReplacement() {
        return !replaced && getClazz().hasReplacement();
    }

    /**
     * Call this method to indicate that this object core has been replaced.
     */
    public void setReplaced() {
        replaced = true;
    }

    /**
     * Returns a replacement core, when available. If there is no replacement
     * available, an {@link Error} will be thrown.
     */
    public ObjectCore getReplacement() {
        if (!hasReplacement()) {
            throw new Error("Replacement not available.");
        }
        return createReplacement(getClazz().getReplacement().getCurrentCore());
    }

    /**
     * Recursively copies the specified core and its ancestors. When the
     * specified core is of the same class as this core, then this core is
     * returned.
     */
    private ObjectCore createReplacement(ObjectCore core) {
        if (core.getClazz() == getClazz()) {
            return this;
        }
        ObjectCore result = core.clone();
        if (core.hasParent()) {
            ObjectCore parent = core.getParent().getCurrentCore();
            result.setParent(new Object(createReplacement(parent)));
        }
        return result;
    }
}
