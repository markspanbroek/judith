package net.spanbroek.judith.runtime;

public abstract class Method {

    protected String[] parameterNames;

    public Method(String... parameterNames) {
        this.parameterNames = parameterNames;
    }

    public Object execute(MethodCall methodCall, Scope scope) {
        methodCall.declareImplicitParameters(scope);
        methodCall.declareExplicitParameters(scope, parameterNames);

        execute(new Scope(scope));

        return scope.get("result");
    }

    protected abstract void execute(Scope scope);

    public int getParameterCount() {
        return parameterNames.length;
    }
}