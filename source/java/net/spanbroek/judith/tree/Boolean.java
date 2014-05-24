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

    @Override
    public boolean equals(Object that) {
        return (that instanceof Boolean) &&
                (((Boolean)that).value == this.value);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}