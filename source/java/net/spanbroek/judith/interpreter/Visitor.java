package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.*;
import net.spanbroek.judith.Exception;
import java.util.*;
import net.spanbroek.judith.tree.Statement;

public class Visitor extends net.spanbroek.judith.tree.Visitor {

    protected Stack<java.lang.Object> stack = new Stack<java.lang.Object>();
    protected World world;
    protected Scope scope;

    public Visitor(World world, Scope scope) {
        this.world = world;
        this.scope = scope;
    }

    public Stack<java.lang.Object> getStack() {
        return stack;
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Alteration node) {

        // calculate the operand
        node.getOperand().accept(this);
        Object operand = (Object)stack.pop();

        // create child object
        Object result = new Object(operand, scope);

        // add objects
        net.spanbroek.judith.tree.ObjectDeclaration[] objects = node.getObjects();
        for (int i=0; i<objects.length; i++) {
            objects[i].getExpression().accept(this);
            result.declare(objects[i].getIdentifier(), (Object)stack.pop());
        }

        // add methods
        net.spanbroek.judith.tree.Method[] methods = node.getMethods();
        for (int i=0; i<methods.length; i++) {
            String name = methods[i].getIdentifier();
            String[] parameterNames = methods[i].getParameters();
            final Statement[] statements = methods[i].getStatements();
            result.declare(new Method(name, parameterNames) {
                @Override
                protected void execute(Scope scope) {
                    new Visitor(world, scope).visit(statements);
                }
            });
        }

        // put result on stack
        stack.push(result);

    }

    @Override
    public void visit(net.spanbroek.judith.tree.Assignment node) {

        // evaluate the expression
        node.getExpression().accept(this);
        Object value = (Object)stack.pop();

        // update the scope
        scope.set(node.getIdentifier(), value);

    }

    @Override
    public void visit(net.spanbroek.judith.tree.Block node) {
        stack.push(scope);
        scope = new Scope(scope);
        visit(node.getStatements());
        scope = (Scope)stack.pop();
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Boolean node) {
        stack.push(world.wrap(node.getValue()));
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Conditional node) {

        // evaluate the expression
        node.getExpression().accept(this);
        java.lang.Object value = world.unwrap((Object)stack.peek());

        // execute statements when expression is true
        if (value instanceof Boolean && (Boolean)value) {
            stack.push(scope);
            scope = new Scope(scope);
            visit(node.getStatements());
            scope = (Scope)stack.pop();
        }

    }

    @Override
    public void visit(net.spanbroek.judith.tree.Do node) {

        // go through all conditionals until no condition holds
        net.spanbroek.judith.tree.Conditional[] conditionals;
        conditionals = node.getConditionals();
        for (boolean resume=true; resume; ) {
            resume = false;
            for (int i=0; !resume && i<conditionals.length; i++) {
                conditionals[i].accept(this);
                java.lang.Object value = world.unwrap((Object)stack.pop());
                resume = value instanceof Boolean && (Boolean)value;
            }
        }

    }

    @Override
    public void visit(net.spanbroek.judith.tree.If node) {

        // go through all conditionals until one condition holds
        net.spanbroek.judith.tree.Conditional[] conditionals;
        conditionals = node.getConditionals();
        boolean done = false;
        for (int i=0; !done && i<conditionals.length; i++) {
            conditionals[i].accept(this);
            java.lang.Object value = world.unwrap((Object)stack.pop());
            done = value instanceof Boolean && (Boolean)value;
        }

    }

    @Override
    public void visit(final net.spanbroek.judith.tree.Lambda node) {

        Object function = new Object(world.get("Function"), scope);

        function.declare(new BasicMethod("evaluate", node.getIdentifiers()) {

            private Visitor visitor = null;

            @Override
            public void execute(Scope scope) {
                visitor = new Visitor(world, scope);
                visitor.visit(node.getExpression());
            }

            @Override
            protected Object getResult(MethodCall methodCall, Scope scope) {
                return (Object) visitor.getStack().pop();
            }
        });

        stack.push(function);

    }

    @Override
    public void visit(final net.spanbroek.judith.tree.LambdaBlock node) {

        Object command = new Object(world.get("Command"), scope);

        command.declare(new BasicMethod("execute", node.getIdentifiers()) {
            @Override
            public void execute(Scope scope) {
                new Visitor(world, scope).visit(node.getStatements());

            }
            @Override
            protected Object getResult(MethodCall methodCall, Scope scope) {
                return methodCall.getSelf();
            }
        });

        stack.push(command);

    }

    @Override
    public void visit(net.spanbroek.judith.tree.Method node) {
        // skip
    }

    @Override
    public void visit(net.spanbroek.judith.tree.MethodCall node) {

        // calculate the operand
        node.getOperand().accept(this);
        Object operand = (Object)stack.pop();

        // calculate the parameters
        net.spanbroek.judith.tree.Expression[] expressions;
        expressions = node.getParameters();
        Object[] parameters = new Object[expressions.length];
        for (int i=0; i<expressions.length; i++) {
            expressions[i].accept(this);
            parameters[i] = (Object)stack.pop();
        }

        // call the method
        try {
            stack.push(operand.call(node.getIdentifier(), parameters));
        }
        catch(Exception exception) {
            // update stack trace when exception is caught
            exception.addToStackTrace(
              node.getLocation().toString() + ": " + node.getIdentifier()
            );
            throw exception;
        }

    }

    @Override
    public void visit(net.spanbroek.judith.tree.Number node) {
        stack.push(world.wrap(node.getValue()));
    }

    @Override
    public void visit(net.spanbroek.judith.tree.ObjectDeclaration node) {
        node.getExpression().accept(this);
        scope.declare(node.getIdentifier(), (Object)stack.pop());
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Program node) {
        visit(node.getStatements());
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Reference node) {
        if (scope.contains(node.getIdentifier())) {
            stack.push(scope.get(node.getIdentifier()));
        } else {
            Object operand = scope.get("self");

            // call the method
            try {
                stack.push(operand.call(node.getIdentifier()));
            }
            catch(Exception exception) {
                // update stack trace when exception is caught
                exception.addToStackTrace(
                  node.getLocation().toString() + ": " + node.getIdentifier()
                );
                throw exception;
            }
        }
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Text node) {
        stack.push(world.wrap(node.getValue()));
    }

    public void visit(net.spanbroek.judith.tree.Node node) {
        node.accept(this);
    }

    protected void visit(net.spanbroek.judith.tree.Statement[] nodes) {

        for (int i=0; i<nodes.length; i++) {

            // visit the statement
            nodes[i].accept(this);

            // a methodcall will leave an object on the stack that is not used
            // in this case, so it must be removed from the stack before
            // proceeding
            if (nodes[i] instanceof net.spanbroek.judith.tree.MethodCall) {
                stack.pop();
            }
        }

    }

}
