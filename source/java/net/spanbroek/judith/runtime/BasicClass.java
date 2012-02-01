package net.spanbroek.judith.runtime;

import java.util.HashMap;
import java.util.Map;

class BasicClass {

    private Map<String, BasicMethod> methods = new HashMap<String, BasicMethod>();

    void declare(BasicMethod method) {
        methods.put(method.getSignature(), method);
    }

    boolean hasMethod(MethodCall methodCall) {
        return methods.containsKey(methodCall.getSignature());
    }

    Object executeMethod(MethodCall methodCall, Scope scope) {
        BasicMethod method = methods.get(methodCall.getSignature());
        return method.execute(methodCall, scope);
    }
}
