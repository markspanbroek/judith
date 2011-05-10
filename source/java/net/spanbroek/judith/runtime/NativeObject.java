package net.spanbroek.judith.runtime;

abstract class NativeObject extends InheritanceObject {

    private java.lang.Object nativeObject = null;

    java.lang.Object getNativeObject() {
        if (nativeObject != null) {
            return nativeObject;
        } else {
            if (hasParent()) {
                return getParent().getNativeObject();
            } else {
                return null;
            }
        }
    }

    void setNativeObject(java.lang.Object object) {
        nativeObject = object;
    }
}
