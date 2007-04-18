package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.runtime.*;

/**
 * Represents a judith method whose statements are executed by the interpreter.
 *
 * @author Mark Spanbroek
 */
public class InterpretedMethod extends Method {

    /**
     * The statements that make up this judith method.
     */
    private net.spanbroek.judith.tree.Statement[] statements;

    /**
     * Constructs a new interpreted judith method with the specified parameter 
     * names and statements.
     */
    public InterpretedMethod(String[] parameters,
      net.spanbroek.judith.tree.Statement[] statements) {

        super(parameters);
        this.statements = statements;

    }

    /**
     * Calls the interpreter visitor for the execution of the method.
     */
    protected void execute(Scope scope) {

        new Visitor(scope, scope.get("self")).visit(statements);

    }

}