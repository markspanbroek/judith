package net.spanbroek.judith.tree;

import java.util.Arrays;

public class Conditional implements Node {

    private Expression expression;
    private Statement[] statements;

    public Conditional(Expression expression, Statement[] statements) {
        this.expression = expression;
        this.statements = statements;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String result = expression + " ";
        for (Statement statement : statements) {
            result += statement;
        }
        return result;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Conditional &&
                expression.equals(((Conditional) that).expression) &&
                Arrays.equals(statements, ((Conditional) that).statements);
    }
}