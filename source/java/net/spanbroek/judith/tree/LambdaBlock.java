package net.spanbroek.judith.tree;

public class LambdaBlock implements Expression {

    private Statement[] statements;

    public LambdaBlock(Statement[] statements) {
        this.statements = statements;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
