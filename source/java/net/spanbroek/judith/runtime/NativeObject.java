package net.spanbroek.judith.runtime;

abstract class NativeObject extends InheritanceObject {

    private java.lang.Object nativeObject = null;

    boolean hasNativeObject() {
        return nativeObject != null;
    }

    java.lang.Object getNativeObject() {
        return nativeObject;
    }

    void setNativeObject(java.lang.Object object) {
        nativeObject = object;
    }
}
