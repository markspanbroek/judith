package net.spanbroek.judith;

import net.spanbroek.judith.runtime.Object;

public class Exception extends java.lang.RuntimeException {

    protected Object object;

    public Exception() {
        super();
    }

    public Exception(String message) {
        super(message);
    }

    public Exception(java.lang.Throwable cause) {
        super(cause);
    }

    public Exception(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public Exception(Object object) {
        this.object = object;
    }

}