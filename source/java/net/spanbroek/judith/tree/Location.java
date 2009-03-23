package net.spanbroek.judith.tree;

public class Location {

    static public Location undefined = new Location();
    
    private String filename;
    private int line;
    private int column;

    public Location() {
        this(null, -1, -1);
    }
    
    public Location(String filename, int line, int column) {
        this.filename = null == filename ? "unknown" : filename;
        this.line = line;
        this.column = column;
    }
    
    public String toString() {
        String result = filename + " ";
        if (0 <= line) {
            result = result + "line " + line;
            if (0 <= column) {
                result = result + " column " + column;
            }
        } 
        return result;
    }

}
