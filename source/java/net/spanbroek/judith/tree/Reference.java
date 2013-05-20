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

}
