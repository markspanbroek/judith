package net.spanbroek.judith.tree;

import java.util.Arrays;

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

    @Override
    public String toString() {
        String result = "do " + conditionals[0];
        for (int i = 1; i < conditionals.length; i++) {
            result += " || " + conditionals[i];
        }
        result += " do";
        return result;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Do &&
                Arrays.equals(conditionals, ((Do) that).conditionals);
    }
}