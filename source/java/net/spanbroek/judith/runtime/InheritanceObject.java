package net.spanbroek.judith.runtime;

abstract class InheritanceObject extends BasicObject {

    /**
     * The parent object. When a method is called which is not present in the
     * current object, then the method call is forwarded to the parent. The
     * top of the inheritance chain is marked by setting the parent equal to
     * null.
     */
    private Object parent = null;

    boolean hasParent() {
        return parent != null;
    }

    Object getParent() {
        return parent;
    }

    void setParent(Object parent) {
        this.parent = parent;
        getScope().declare("parent", new Parent(parent));
    }

    @Override
    Object call(MethodCall methodCall) {
        if (!getClazz().hasMethod(methodCall) && hasParent()) {
            return getParent().call(methodCall);
        }

        return super.call(methodCall);
    }

    @Override
    InheritanceObject copy() {
        InheritanceObject result = (InheritanceObject) super.copy();
        if (hasParent()) {
            result.setParent(getParent().copy());
        }
        return result;
    }
}
