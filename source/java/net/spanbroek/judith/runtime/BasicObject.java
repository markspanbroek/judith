package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Error;

abstract class BasicObject implements Cloneable {

    /**
     * The scope of the object, which contains the inner objects, and has a
     * reference to the outer objects. Inner objects are those objects which are
     * declared within the object, whereas outer objects are declared outside
     * the object declaration. For example:
     * <pre>
     *   object outer := "This is an outer object of the foo object"
     *   object foo := Object
     *   |[
     *      object inner := "This is an inner object of the foo object"
     *   ]|
     * </pre>
     */
    private Scope scope;

    /**
     * The class of the object. Contains the methods of this object.
     */
    private ReplaceableClass clazz = new ReplaceableClass();

    Scope getScope() {
        return scope;
    }

    void setScope(Scope scope) {
        this.scope = scope;
    }

    ReplaceableClass getClazz() {
        return clazz;
    }

    void setClazz(ReplaceableClass clazz) {
        this.clazz = clazz;
    }

    BasicObject copy() {
        BasicObject result = clone();
        result.setScope(getScope().copy());
        return result;
    }

    @Override
    public BasicObject clone() {
        try {
            return (BasicObject) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new Error(exception);
        }
    }
}
