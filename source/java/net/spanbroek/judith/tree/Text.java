package net.spanbroek.judith.tree;

public class Text implements Expression {

    private String value;

    public Text(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}