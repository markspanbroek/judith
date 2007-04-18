package net.spanbroek.judith.tree;

public class Reference implements Expression {

    private String identifier;

    public Reference(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}