package net.spanbroek.judith.tree;

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

}