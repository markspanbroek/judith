package net.spanbroek.judith.runtime;

public abstract class Method {

    protected String[] parameterNames;

    public Method(String... parameters) {
        this.parameterNames = parameters;
    }

    public Object execute(Object[] parameters, Object self, Object caller, Scope scope) {
        declareImplicitParameters(scope, self, caller);
        declareParameters(scope, parameters);
        
        execute(new Scope(scope));

        return scope.get("result");
    }

    protected abstract void execute(Scope scope);

    public int getParameterCount() {
        return parameterNames.length;
    }

    protected void declareImplicitParameters(Scope scope, Object self, Object caller) {
        scope.declare("self", self);
        scope.declare("caller", caller);
        scope.declare("result", self);
    }

    protected void declareParameters(Scope scope, Object[] parameters) {
        assert parameters.length == parameterNames.length;
        for (int i = 0; i < this.parameterNames.length; i++) {
            scope.declare(this.parameterNames[i], parameters[i]);
        }
    }
}