package net.spanbroek.judith.tree;

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

}
