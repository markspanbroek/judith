package net.spanbroek.judith.tree;

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

}