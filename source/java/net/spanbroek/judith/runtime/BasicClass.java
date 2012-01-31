package net.spanbroek.judith.runtime;

import java.util.HashMap;
import java.util.Map;

class BasicClass {

    private Map<String, Method> methods = new HashMap<String, Method>();

    void declare(Method method) {
        methods.put(method.getSignature(), method);
    }

    boolean hasMethod(MethodCall methodCall) {
        return methods.containsKey(methodCall.getSignature());
    }

    Object executeMethod(MethodCall methodCall, Scope scope) {
        Method method = methods.get(methodCall.getSignature());
        return method.execute(methodCall, scope);
    }
}
