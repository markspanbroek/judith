package net.spanbroek.judith.tree;

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

}