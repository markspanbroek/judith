package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Method;
import net.spanbroek.judith.runtime.Scope;
import net.spanbroek.judith.runtime.World;
import net.spanbroek.judith.tree.Expression;

public class InterpretedExpression extends Method {

    private World world;
    private Expression expression;

    public InterpretedExpression(String[] parameters, Expression expression, World world) {
        super(parameters);
        this.world = world;
        this.expression = expression;
    }

    @Override
    public Object execute(Object[] parameters, Object self, Object caller, Scope scope) {
        for (int i=0; i<this.parameters.length; i++) {
            scope.declare(this.parameters[i], parameters[i]);
        }
        return evaluate(expression, scope);
    }

    private Object evaluate(Expression expression, Scope scope) {
        Visitor visitor = new Visitor(world, scope, scope.get("self"));
        visitor.visit(expression);
        return (Object) visitor.getStack().pop();
    }

    @Override
    protected void execute(Scope scope) {
        throw new UnsupportedOperationException("Not supported");
    }

}
