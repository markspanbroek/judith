package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.objects.Boolean;
import net.spanbroek.judith.objects.Number;
import net.spanbroek.judith.objects.Text;
import java.util.*;

public class Visitor extends net.spanbroek.judith.tree.Visitor {

    private Context context;
    private Object self;
    private Stack stack;
    
    public Visitor(Context context, Object self) {
        this.context = context;
        this.self = self;
        this.stack = new Stack();
    }
    
    public void visit(net.spanbroek.judith.tree.Alteration node) {
    
        // calculate the operand
        node.getOperand().accept(this);
        Object operand = (Object)stack.pop();
        
        // create child object
        Object result = operand.create();
        result.getContext().setParent(context);
        
        // create new context
        stack.push(context);
        context = result.getContext();
        
        // add objects
        net.spanbroek.judith.tree.Object[] objects = node.getObjects();
        for (int i=0; i<objects.length; i++) {
            objects[i].getExpression().accept(this);
            context.declare(objects[i].getIdentifier(), (Object)stack.pop());
        }
        
        // add methods
        net.spanbroek.judith.tree.Method[] methods = node.getMethods();
        for (int i=0; i<methods.length; i++) {
            Method method = new InterpretedMethod(
              context,
              methods[i].getParameters(),
              methods[i].getStatements()
            );
            operand.declare(
              methods[i].getIdentifier(), 
              methods[i].getParameters().length, 
              method
            );
        }
        
        // restore old context
        context = (Context)stack.pop();
        
        // put result on stack
        stack.push(result);
        
    }
    
    public void visit(net.spanbroek.judith.tree.Assignment node) {
    
        // evaluate the expression
        node.getExpression().accept(this);
        Object value = (Object)stack.pop();
        
        // update the context
        context.set(node.getIdentifier(), value);
    
    }
    
    public void visit(net.spanbroek.judith.tree.Boolean node) {
        Boolean b = (Boolean)Interpreter.getInstance().getBoolean();
        stack.push(b.create(node.getValue()));
    }
    
    public void visit(net.spanbroek.judith.tree.Conditional node) {
    
        // evaluate the expression
        node.getExpression().accept(this);
        Object value = (Object)stack.peek();
        
        // execute statements when expression is true
        if (value instanceof Boolean && ((Boolean)value).getValue()) {
            stack.push(context);
            context = new Context(context);
            visit(node.getStatements());
            context = (Context)stack.pop();
        }
        
    }
    
    public void visit(net.spanbroek.judith.tree.Do node) {

        // go through all conditionals until no condition holds
        net.spanbroek.judith.tree.Conditional[] conditionals;
        conditionals = node.getConditionals();
        for (boolean resume=true; resume; ) {
            resume = false;
            for (int i=0; !resume && i<conditionals.length; i++) {
                conditionals[i].accept(this);
                Object value = (Object)stack.pop();
                resume = value instanceof Boolean 
                  && ((Boolean)value).getValue();
            }
        }
    
    }
    
    public void visit(net.spanbroek.judith.tree.If node) {
    
        // go through all conditionals until one condition holds
        net.spanbroek.judith.tree.Conditional[] conditionals;
        conditionals = node.getConditionals();
        boolean done = false;
        for (int i=0; !done && i<conditionals.length; i++) {
            conditionals[i].accept(this);
            Object value = (Object)stack.pop();
            done = value instanceof Boolean
              && ((Boolean)value).getValue();
        }
        
    }
    
    public void visit(net.spanbroek.judith.tree.Language node) {
        visit(node.getStatements());
    }
    
    public void visit(net.spanbroek.judith.tree.Method node) {
        // skip
    }
    
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
        stack.push(operand.call(node.getIdentifier(), parameters, self));
    
    }
    
    public void visit(net.spanbroek.judith.tree.Number node) {
        Number n = (Number)Interpreter.getInstance().getNumber();
        stack.push(n.create(node.getValue()));
    }
    
    public void visit(net.spanbroek.judith.tree.Object node) {
        node.getExpression().accept(this);
        context.declare(node.getIdentifier(), (Object)stack.pop());
    }
    
    public void visit(net.spanbroek.judith.tree.Reference node) {
        stack.push(context.get(node.getIdentifier()));
    }
    
    public void visit(net.spanbroek.judith.tree.Text text) {
        Text t = (Text)Interpreter.getInstance().getText();
        stack.push(t.create(text.getValue()));
    }

    /**
     * Visits a list of statements.
     */    
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
