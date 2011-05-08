package net.spanbroek.judith.runtime;

/**
 * Represents a judith method.
 *
 * @author Mark Spanbroek
 */
public abstract class Method {

    /**
     * The names of the parameters of this method.
     */
    protected String[] parameters;

    /**
     * Constructs a new method using the specified parameter names.
     */
    public Method(String... parameters) {

        this.parameters = parameters;

    }

    /**
     * Executes this method using the specified parameter values and scope.
     * The amount of parameter values should be equal to the number of parameter
     * names that were specified in the constructor. The scope should contain
     * a variable 'self'.
     */
    public Object execute(Object[] parameters, Object self, Object caller, Scope scope) {

        scope.declare("self", self);
        scope.declare("caller", caller);
        scope.declare("result", self);

        for (int i=0; i<this.parameters.length; i++) {
            scope.declare(this.parameters[i], parameters[i]);
        }
        
        execute(new Scope(scope));
        return scope.get("result");

    }

    /**
     * The actual execution of the method.
     */
    protected abstract void execute(Scope scope);

    /**
     * Returns the number of parameters of this method.
     */
    public int getParameterCount() {

        return parameters.length;

    }

}