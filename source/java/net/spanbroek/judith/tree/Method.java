package net.spanbroek.judith.tree;

import java.util.Arrays;

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

    @Override
    public String toString() {
        String result = "method " + identifier;
        if (parameters.length > 0) {
            result += "(";
            for (int i=0; i<parameters.length; i++) {
                if (i>0) {
                    result += ", ";
                }
                result = result + parameters[i];
            }
            result += ")";
        }
        result += "\n[";
        for (Statement statement: statements) {
            result += "\n  " + statement;
        }
        result += "\n]";
        return result;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Method &&
                identifier.equals(((Method)that).identifier) &&
                Arrays.equals(parameters, ((Method)that).parameters) &&
                Arrays.equals(statements, ((Method)that).statements);
    }
}