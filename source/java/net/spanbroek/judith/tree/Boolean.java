package net.spanbroek.judith.tree;

public class Boolean implements Expression {

    private boolean value;

    public Boolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}