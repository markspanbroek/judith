package net.spanbroek.judith.tree;

public class Object implements Statement {

    private String identifier;
    private Expression expression;

    public Object(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}