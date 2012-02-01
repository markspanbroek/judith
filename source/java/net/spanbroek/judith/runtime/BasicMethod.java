package net.spanbroek.judith.runtime;


public abstract class BasicMethod {

    private String name;
    protected String[] parameterNames;

    public BasicMethod(String name, String... parameterNames)
    {
        this.name = name;
        this.parameterNames = parameterNames;
    }

    public String getSignature() {
        return name + "," + parameterNames.length;
    }

    public Object execute(MethodCall methodCall, Scope scope) {
        methodCall.declareExplicitParameters(scope, parameterNames);
        execute(new Scope(scope));
        return getResult(methodCall, scope);
    }

    protected abstract void execute(Scope scope);

    protected abstract Object getResult(MethodCall methodCall, Scope scope);

}
