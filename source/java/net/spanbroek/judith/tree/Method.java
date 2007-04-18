package net.spanbroek.judith.tree;

public class Method implements Node {

    private String identifier;
    private String[] parameters;
    private Statement[] statements;

    public Method(String identifier, String[] parameters,
      Statement[] statements) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.statements = statements;    
    }

    public String getIdentifier() {
        return identifier;
    }

    public String[] getParameters() {
        return parameters;
    }

    public Statement[] getStatements() {
        return statements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}