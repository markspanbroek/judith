package net.spanbroek.judith.tree;

public class Reference implements Expression {

    private String identifier;
    private Location location = Location.undefined;

    public Reference(String identifier, Location location) {
        this(identifier);
        this.location = location;
    }

    public Reference(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Location getLocation() {
        return location;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof Reference &&
                identifier.equals(((Reference) that).identifier);
    }
}
