package net.spanbroek.judith.tree;

public interface Node {

    void accept(Visitor visitor);

}