package net.spanbroek.judith.tree;

import java.util.Arrays;

public class Program implements Node {

    private Statement[] statements;

    public Program(Statement[] statements) {
        this.statements = statements;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String result = "";
        for (Statement statement : statements) {
            result += statement + "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Program &&
                Arrays.equals(statements, ((Program)that).statements);
    }
}