package net.spanbroek.judith.tree;

import java.util.Arrays;

public class LambdaBlock implements Expression {

    private String[] identifiers;
    private Statement[] statements;

    public LambdaBlock(Statement[] statements) {
        this(new String[]{}, statements);
    }

    public LambdaBlock(String[] identifiers, Statement[] statements) {
        this.identifiers = identifiers;
        this.statements = statements;
    }

    public String[] getIdentifiers() {
        return identifiers;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String parameters = Arrays.toString(identifiers);
        parameters = parameters.substring(1, parameters.length() - 1);
        String result = "[" + parameters;
        if (parameters.length() > 0) {
            result += " ->";
        }
        for (Statement statement : statements) {
            result += "\n  " + statement;
        }
        return result + "\n]";
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof LambdaBlock &&
                Arrays.equals(identifiers, ((LambdaBlock) that).identifiers) &&
                Arrays.equals(statements, ((LambdaBlock) that).statements);
    }


}
