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

    @Override
    public String toString() {
        return "object " + identifier + " := " + expression;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof ObjectDeclaration &&
                identifier.equals(((ObjectDeclaration)that).identifier) &&
                expression.equals(((ObjectDeclaration)that).expression);
    }

}