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

    private World world;

    /**
     * Constructs a new interpreted judith method with the specified parameter
     * names and statements.
     */
    public InterpretedMethod(String name, String[] parameters,
      net.spanbroek.judith.tree.Statement[] statements, World world) {

        super(name, parameters);
        this.statements = statements;
        this.world = world;

    }

    /**
     * Calls the interpreter visitor for the execution of the method.
     */
    protected void execute(Scope scope) {

        new Visitor(world, scope).visit(statements);

    }

}
