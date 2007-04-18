package net.spanbroek.judith.tree;

public class MethodCall implements Expression, Statement{

    private Expression operand;
    private String identifier;
    private Expression[] parameters;

    public MethodCall(Expression operand, String identifier
      , Expression[] parameters) {
        this.operand = operand;
        this.identifier = identifier;
        this.parameters = parameters;
    }
    
    public Expression getOperand() {
        return operand;
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public Expression[] getParameters() {
        return parameters;
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}