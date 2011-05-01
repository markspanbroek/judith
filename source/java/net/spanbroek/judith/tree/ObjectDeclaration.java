package net.spanbroek.judith.tree;

public class ObjectDeclaration implements Statement {

    private String identifier;
    private Expression expression;

    public ObjectDeclaration(String identifier, Expression expression) {
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