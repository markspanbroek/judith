package net.spanbroek.judith.tree;

public class Do implements Statement {

    private Conditional[] conditionals;
    
    public Do(Conditional[] conditionals) {
        this.conditionals = conditionals;
    }
    
    public Conditional[] getConditionals() {
        return conditionals;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}