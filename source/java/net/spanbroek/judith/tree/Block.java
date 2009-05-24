package net.spanbroek.judith.tree;

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

}