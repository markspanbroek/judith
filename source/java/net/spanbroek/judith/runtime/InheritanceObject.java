package net.spanbroek.judith.runtime;

abstract class InheritanceObject extends BasicObject {

    private Object parent = null;

    boolean hasParent() {
        return parent != null;
    }

    Object getParent() {
        return parent;
    }

    void setParent(Object parent) {
        this.parent = parent;
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

    @Override
    boolean isCompatibleWith(Object object) {
        if (super.isCompatibleWith(object)) {
            return true;
        } else {
            if (hasParent()) {
                return getParent().isCompatibleWith(object);
            } else {
                return false;
            }
        }
    }

    boolean isDescendantOf(Object object) {
        if (hasParent()) {
            if (getParent().equals(object)) {
                return true;
            } else {
                return getParent().isDescendantOf(object);
            }
        } else {
            return false;
        }
    }
}
