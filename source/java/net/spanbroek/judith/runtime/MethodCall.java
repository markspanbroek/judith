package net.spanbroek.judith.runtime;

public class MethodCall {

    private Object self;
    private String name;
    private Object[] parameters;
    private Parent parent;

    public MethodCall(Object self, String name, Object[] parameters) {
        this.self = self;
        this.name = name;
        this.parameters = parameters;
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

    public void declareImplicitParameters(Scope scope)
    {
        scope.declare("self", getSelf());
        scope.declare("parent", getParent());
        scope.declare("result", getSelf());
    }

    public void declareExplicitParameters(Scope scope, String... parameterNames)
    {
        for (int i=0; i<parameterNames.length; i++) {
            scope.declare(parameterNames[i], parameters[i]);
        }
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
