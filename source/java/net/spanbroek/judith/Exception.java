package net.spanbroek.judith;

import net.spanbroek.judith.runtime.Object;
import java.util.*;

public class Exception extends java.lang.RuntimeException {

    protected Object object = null;
    
    protected List<String> stackTrace = new LinkedList<String>();

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

    public void addToStackTrace(String traceElement) {
        stackTrace.add(traceElement);
    }

    public Object getObject() {
        return object;
    }

    public String toString() {
        String result = getObjectAsText();
        if (result == null) {
            result = super.toString();
        }
        for (String element : stackTrace) {
            result = result + "\n    " + element;
        }
        return result;
    }
    
    private String getObjectAsText() {
        if (object != null) {
            java.lang.Object text = object.call("asText").getNativeObject();
            if (text != null) {
                return text.toString();
            }
        }
        return null;    
    }

}
