package net.spanbroek.judith;

import net.spanbroek.judith.runtime.Object;

public class Error extends java.lang.Error {

    protected Object object = null;

    public Error() {
        super();
    }

    public Error(String message) {
        super(message);
    }

    public Error(java.lang.Throwable cause) {
        super(cause);
    }

    public Error(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public Error(Object object) {
        this.object = object;
    }

    public String toString() {
        if (object != null) {
            java.lang.Object text = object.call("asText").getNativeObject();
            if (text != null) {
                return text.toString();
            }
        }
        return super.toString();
    }

}