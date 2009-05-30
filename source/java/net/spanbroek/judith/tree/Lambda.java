package net.spanbroek.judith.tree;

public class Lambda implements Expression {

    private String[] identifiers;
    private Expression expression;

    public Lambda(String[] identifiers, Expression expression) {
        this.identifiers = identifiers;
        this.expression = expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String[] getIdentifiers() {
        return identifiers;
    }

    public Expression getExpression() {
        return expression;
    }
    
}
