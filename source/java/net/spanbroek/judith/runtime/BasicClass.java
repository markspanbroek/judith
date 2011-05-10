package net.spanbroek.judith.runtime;

import java.util.HashMap;
import java.util.Map;

class BasicClass {

    private Map<String, Method> methods = new HashMap<String, Method>();

    void declare(String name, Method method) {
        String identifier = createIdentifierFor(name, method.getParameterCount());
        methods.put(identifier, method);
    }

    boolean hasMethod(MethodCall methodCall) {
        String identifier = createIdentifierFor(methodCall);
        return methods.containsKey(identifier);
    }

    Object executeMethod(MethodCall methodCall, Scope scope) {
        Method method = methods.get(createIdentifierFor(methodCall));
        return method.execute(methodCall, scope);
    }

    private String createIdentifierFor(MethodCall methodCall) {
        String name = methodCall.getName();
        int parameterCount = methodCall.getParameters().length;
        return createIdentifierFor(name, parameterCount);
    }

    private String createIdentifierFor(String methodName, int parameterCount) {
        return methodName + "," + parameterCount;
    }
}
