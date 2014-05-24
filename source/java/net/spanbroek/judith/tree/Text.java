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

    @Override
    public boolean equals(Object that) {
        return (that instanceof Text) &&
                (((Text)that).value.equals(this.value));
    }

    @Override
    public String toString() {
        return value;
    }

}