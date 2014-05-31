package net.spanbroek.judith.tree;

import java.util.Arrays;

public class MethodCall implements Expression, Statement{

    private Expression operand;
    private String identifier;
    private Expression[] parameters;
    private Location location = Location.undefined;

    public MethodCall(Expression operand, String identifier
      , Expression[] parameters, Location location) {
        this(operand, identifier, parameters);
        this.location = location;
    }

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
    
    public Location getLocation() {
        return location;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String parameterString = Arrays.toString(parameters);
        parameterString = parameterString.substring(1, parameterString.length() -1);
        return operand + "." + identifier + '('+ parameterString +')';
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof MethodCall &&
                operand.equals(((MethodCall) that).operand) &&
                identifier.equals(((MethodCall) that).identifier) &&
                Arrays.equals(parameters, ((MethodCall) that).parameters) &&
                location.equals(((MethodCall) that).location);
    }
}
