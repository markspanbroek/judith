package net.spanbroek.judith.tree;

public class Language implements Node {

    private Statement[] statements;
    
    public Language(Statement[] statements) {
        this.statements = statements;
    }
    
    public Statement[] getStatements() {
        return statements;
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}