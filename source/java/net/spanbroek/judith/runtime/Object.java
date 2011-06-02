package net.spanbroek.judith.runtime;

public class Object {

    private ReplaceableObject replaceable;

    public Object(Object parent, Scope scope) {
        this(scope);
        replaceable.setParent(parent.call("copy"));
    }

    public Object(Scope scope) {
        this(new ReplaceableObject());
        replaceable.setScope(new Scope(scope));
    }

    Object(ReplaceableObject core) {
        this.replaceable = core;
    }

    ReplaceableObject getCurrentCore() {
        return replaceable;
    }

    void setCore(ReplaceableObject core) {
        this.replaceable = core;
    }
    public void declare(String name, Object object) {
        resolveReplacements();
        getCurrentCore().getScope().declare(name, object);
    }

    public void declare(String name, Method method) {
        resolveReplacements();
        getCurrentCore().getClazz().declare(name, method);
    }

    public Object call(String name, Object... parameters) {
        return call(new MethodCall(this, name, parameters));
    }

    protected Object call(MethodCall methodCall) {
        resolveReplacements();
        return getCurrentCore().call(methodCall);
    }

    public java.lang.Object getNativeObject() {
        resolveReplacements();
        return getCurrentCore().getNativeObject();
    }

    public void setNativeObject(java.lang.Object object) {
        resolveReplacements();
        getCurrentCore().setNativeObject(object);
    }

    /**
     * Determines whether or not this object is compatible with the specified 
     * object. For each object 'A' holds that it is compatible with object 'B',
     * when 'A' is of the same class as 'B', or if the parent of 'A' is
     * compatible with 'B'.
     */
    public boolean isCompatibleWith(Object object) {
        resolveReplacements();
        return getCurrentCore().isCompatibleWith(object);
    }

    boolean isDescendantOf(Object object) {
        resolveReplacements();
        return getCurrentCore().isDescendantOf(object);
    }

    public boolean equals(Object object) {
        resolveReplacements();
        object.resolveReplacements();
        return getCurrentCore() == object.getCurrentCore();
    }

    public Object copy() {
        resolveReplacements();
        return new Object(getCurrentCore().copy());
    }

    public synchronized void replace(Object object) {
        resolveReplacements();
        getCurrentCore().getClazz().setReplacement(object);
    }

    ReplaceableClass getClazz() {
        resolveReplacements();
        return getCurrentCore().getClazz();
    }

    private void resolveReplacements() {
        while (getCurrentCore().hasReplacement()) {
            ReplaceableObject oldCore = getCurrentCore();
            setCore(getCurrentCore().getReplacement());
            oldCore.markAsReplaced();
        }
    }
}