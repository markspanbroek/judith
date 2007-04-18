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

}