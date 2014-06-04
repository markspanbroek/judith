package net.spanbroek.judith.tree;

import java.util.Arrays;

public class Block implements Statement {

    private Statement[] statements;

    public Block(Statement[] statements) {
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
        String result = "[\n";
        for (Statement statement : statements) {
            result += "  " + statement + "\n";
        }
        return result + "]";
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Block &&
                Arrays.equals(statements, ((Block) that).statements);
    }
}
