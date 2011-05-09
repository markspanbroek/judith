package net.spanbroek.judith.runtime;

public abstract class Method {

    protected String[] parameterNames;

    public Method(String... parameters) {
        this.parameterNames = parameters;
    }

    public Object execute(MethodCall methodCall, Scope scope) {
        declareImplicitParameters(scope, methodCall);
        declareParameters(scope, methodCall);
        
        execute(new Scope(scope));

        return scope.get("result");
    }

    protected abstract void execute(Scope scope);

    public int getParameterCount() {
        return parameterNames.length;
    }

    protected void declareImplicitParameters(Scope scope, MethodCall methodCall) {
        scope.declare("self", methodCall.getSelf());
        scope.declare("caller", methodCall.getCaller());
        scope.declare("result", methodCall.getSelf());
    }

    protected void declareParameters(Scope scope, MethodCall methodCall) {
        Object[] parameters = methodCall.getParameters();

        assert parameters.length == parameterNames.length;

        for (int i = 0; i < this.parameterNames.length; i++) {
            scope.declare(this.parameterNames[i], parameters[i]);
        }
    }
}