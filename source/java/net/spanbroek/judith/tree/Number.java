package net.spanbroek.judith.tree;

public class Number implements Expression {

    private double value;

    public Number(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof Number) &&
                (((Number)that).value == this.value);

    }

    @Override
    public String toString() {
        return "" + value;
    }
}