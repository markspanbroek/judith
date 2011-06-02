package net.spanbroek.judith.runtime;

public class MethodCall {

    private Object caller;
    private Object self;
    private String name;
    private Object[] parameters;
    private Parent parent;

    public MethodCall(Object self, String name, Object[] parameters, Object caller) {
        this.self = self;
        this.name = name;
        this.parameters = parameters;
        this.caller = caller;
    }

    public Object getCaller() {
        return caller;
    }

    public Object getSelf() {
        return self;
    }

    public String getName() {
        return name;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String description = name;
        if (parameters.length > 0) {
            description += "(";
            for (int i = 0; i < parameters.length; i++) {
                if (i > 0) {
                    description += ", ";
                }
                description += "object";
                if (parameters.length > 1) {
                    description += i;
                }
            }
            description += ")";
        }
        return description;
    }
}
