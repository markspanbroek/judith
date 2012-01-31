package net.spanbroek.judith.runtime;

public abstract class Method {

    private String name;
    protected String[] parameterNames;

    public Method(String name, String... parameterNames)
    {
        this.name = name;
        this.parameterNames = parameterNames;
    }

    public String getSignature() {
        return name + "," + parameterNames.length;
    }

    public Object execute(MethodCall methodCall, Scope scope) {
        methodCall.declareImplicitParameters(scope);
        methodCall.declareExplicitParameters(scope, parameterNames);

        execute(new Scope(scope));

        return scope.get("result");
    }

    protected abstract void execute(Scope scope);
}