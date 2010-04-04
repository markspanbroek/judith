package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.tree.Assignment;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.*;
import net.spanbroek.judith.Exception;
import java.util.*;

public class Visitor extends net.spanbroek.judith.tree.Visitor {

    protected Stack stack = new Stack();
    protected World world;
    protected Scope scope;
    protected Object self;

    public Visitor(World world, Scope scope, Object self) {
        this.world = world;
        this.scope = scope;
        this.self = self;
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Alteration node) {

        // calculate the operand
        node.getOperand().accept(this);
        Object operand = (Object)stack.pop();

        // create child object
        Object result = new Object(operand, scope);

        // add objects
        net.spanbroek.judith.tree.Object[] objects = node.getObjects();
        for (int i=0; i<objects.length; i++) {
            objects[i].getExpression().accept(this);
            result.declare(objects[i].getIdentifier(), (Object)stack.pop());
        }

        // add methods
        net.spanbroek.judith.tree.Method[] methods = node.getMethods();
        for (int i=0; i<methods.length; i++) {
            Method method = new InterpretedMethod(
              methods[i].getParameters(),
              methods[i].getStatements(),
              world
            );
            result.declare(
              methods[i].getIdentifier(),
              method
            );
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
    public void visit(net.spanbroek.judith.tree.Lambda node) {

        Object function = new Object(world.get("Function"), scope);

        Assignment resultAssignment = new Assignment(
          "result",
          node.getExpression()
        );

        Method evaluateMethod = new InterpretedMethod(
          node.getIdentifiers(),
          new Assignment[]{ resultAssignment },
          world
        );

        function.declare("evaluate", evaluateMethod);
        
        stack.push(function);

    }

    @Override
    public void visit(net.spanbroek.judith.tree.LambdaBlock node) {

        Object command = new Object(world.get("Command"), scope);

        Method runMethod = new InterpretedMethod(
          node.getIdentifiers(),
          node.getStatements(),
          world
        );

        command.declare("run", runMethod);
        
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
            stack.push(operand.call(node.getIdentifier(), parameters, self));
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
    public void visit(net.spanbroek.judith.tree.Object node) {
        node.getExpression().accept(this);
        scope.declare(node.getIdentifier(), (Object)stack.pop());
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Program node) {
        visit(node.getStatements());
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Reference node) {
        stack.push(scope.get(node.getIdentifier()));
    }

    @Override
    public void visit(net.spanbroek.judith.tree.Text node) {
        stack.push(world.wrap(node.getValue()));
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
