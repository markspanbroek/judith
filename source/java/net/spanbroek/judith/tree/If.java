package net.spanbroek.judith.tree;

public class If implements Statement {

    private Conditional[] conditionals;

    public If(Conditional[] conditionals) {
        this.conditionals = conditionals;
    }

    public Conditional[] getConditionals() {
        return conditionals;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}