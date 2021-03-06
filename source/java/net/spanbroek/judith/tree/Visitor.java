package net.spanbroek.judith.tree;

public abstract class Visitor {

    public void visit(Alteration node) {}
    public void visit(Assignment node) {}
    public void visit(Block node) {}
    public void visit(Boolean node) {}
    public void visit(Conditional node) {}
    public void visit(Do node) {}
    public void visit(If node) {}
    public void visit(Lambda node) {}
    public void visit(LambdaBlock node) {}
    public void visit(Method node) {}
    public void visit(MethodCall node) {}
    public void visit(Number node) {}
    public void visit(ObjectDeclaration node) {}
    public void visit(Program node) {}
    public void visit(Reference node) {}
    public void visit(Text node) {}

}
