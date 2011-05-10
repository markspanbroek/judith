package net.spanbroek.judith.runtime;

import java.util.HashMap;
import java.util.Map;

class BasicClass {

    private Map<String, Method> methods = new HashMap<String, Method>();

    void declare(String name, Method method) {
        String identifier = name + "," + method.getParameterCount();
        methods.put(identifier, method);
    }

    Method getMethod(String name, int parameterCount) {
        String identifier = name + "," + parameterCount;
        return methods.get(identifier);
    }

}
