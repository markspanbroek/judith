package net.spanbroek.judith.interpreter;

/**
 * Represents a judith method whose statements are executed by the interpreter.
 *
 * @author Mark Spanbroek
 */
public class InterpretedMethod implements Method {

    /**
     * The statements that make up this judith method.
     */
    private net.spanbroek.judith.tree.Statement[] statements;
    
    /**
     * The names of this judith method's parameters.
     */
    private String[] parameters;

    /**
     * Constructs a new interpreted judith method with the specified parameter 
     * names and statements.
     */
    public InterpretedMethod(String[] parameters
      , net.spanbroek.judith.tree.Statement[] statements) {
      
        this.statements = statements;
        this.parameters = parameters;
        
    }

    /**
     * Executes this judith method with the specified parameters, caller object,
     * self object, instance object and context.
     */
    public Object call(Object[] parameters, Object caller, Object self
      , Object instance, Context context) {

        Context c = instance.getContext().shallowCopy();
        c.setParent(context);
        c = new Context(c);
        c.declare("result", self);
        c.declare("self", self);
        c.declare("caller", caller);
        c = new Context(c);
          
        // add parameter variables
        for(int i=0; i<parameters.length; i++) {
            c.declare(this.parameters[i], parameters[i]);
        }
        
        // use the interpreter visitor to execute the statements
        new Visitor(c, self).visit(statements);
        
        // return the result
        return c.getParent().get("result");
        
    }

}
