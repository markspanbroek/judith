package net.spanbroek.judith.tree;

import java.util.Arrays;

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

    @Override
    public String toString() {
        String parameters = Arrays.toString(identifiers);
        parameters = parameters.substring(1, parameters.length()-1);
        return "(" + parameters + " -> " + expression + ')';
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Lambda &&
                Arrays.equals(identifiers, ((Lambda)that).identifiers) &&
                expression.equals(((Lambda)that).expression);
    }
}
