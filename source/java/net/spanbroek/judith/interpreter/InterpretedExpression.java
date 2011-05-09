package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Method;
import net.spanbroek.judith.runtime.MethodCall;
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
    public Object execute(MethodCall methodCall, Scope scope) {
        declareParameters(scope, methodCall);
        return evaluate(expression, scope, methodCall.getSelf());
    }

    private Object evaluate(Expression expression, Scope scope, Object self) {
        Visitor visitor = new Visitor(world, scope, self);
        visitor.visit(expression);
        return (Object) visitor.getStack().pop();
    }

    @Override
    protected void execute(Scope scope) {
        throw new UnsupportedOperationException("Not supported");
    }

}
