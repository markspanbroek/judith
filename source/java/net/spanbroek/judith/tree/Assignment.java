package net.spanbroek.judith.tree;

public class Assignment implements Statement {

    private String identifier;
    private Expression expression;

    public Assignment(String identifier, Expression expression) {
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
        return identifier + " := " + expression;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Assignment &&
                identifier.equals(((Assignment)that).identifier) &&
                expression.equals(((Assignment)that).expression);
    }
}