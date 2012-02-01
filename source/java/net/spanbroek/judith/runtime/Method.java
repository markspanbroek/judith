package net.spanbroek.judith.runtime;

public abstract class Method extends BasicMethod {

    public Method(String name, String... parameterNames)
    {
        super(name, parameterNames);
    }

    @Override
    public Object execute(MethodCall methodCall, Scope scope) {
        methodCall.declareImplicitParameters(scope);
        return super.execute(methodCall, scope);
    }

    @Override
    protected Object getResult(MethodCall methodCall, Scope scope) {
        return scope.get("result");
    }

}