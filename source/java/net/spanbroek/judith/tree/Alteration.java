package net.spanbroek.judith.tree;

import java.util.Arrays;

public class Alteration implements Expression {

    private Expression operand;
    private ObjectDeclaration[] objects;
    private Method[] methods;

    public Alteration(Expression operand, ObjectDeclaration[] objects, Method[] methods) {
        this.operand = operand;
        this.objects = objects;
        this.methods = methods;
    }

    public Expression getOperand() {
        return operand;
    }

    public ObjectDeclaration[] getObjects() {
        return objects;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        String result = operand + "\n|[";
        for (ObjectDeclaration object : objects) {
            result += "\n  " + object;
        }
        for (Method method : methods) {
            result += "\n  " + method;
        }
        return result + "\n]|";
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Alteration &&
                operand.equals(((Alteration)that).operand) &&
                Arrays.equals(objects, ((Alteration)that).objects) &&
                Arrays.equals(methods, ((Alteration)that).methods);
    }
}