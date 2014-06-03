package net.spanbroek.judith.tree;

import java.util.Arrays;

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

    @Override
    public String toString() {
        String result = "if " + conditionals[0];
        for (int i = 1; i < conditionals.length; i++) {
            result += " || " + conditionals[i];
        }
        result += " fi";
        return result;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof If &&
                Arrays.equals(conditionals, ((If) that).conditionals);
    }
}