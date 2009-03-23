package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.parser.Parser;
import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.runtime.*;
import net.spanbroek.judith.Exception;
import java.io.*;

public class Interpreter {

    protected Scope scope;

    public Interpreter(Scope scope) {
        this.scope = scope;
    }

    public Interpreter() {
        this(World.getInstance());
    }

    public void interpret(Statement... statements) {
        new Visitor(scope, World.getInstance().get("self")).visit(statements);
    }

    public void interpret(Program program) {
        interpret(program.getStatements());
    }

    public void interpret(Reader reader, String filename) throws IOException {
        Program program = Parser.parse(reader, filename);
        interpret(program);
    }

    public void interpret(String string) {
        try {
            interpret(new StringReader(string), null);
        }
        catch(IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void main(String[] arguments) throws IOException {
        try {        
            new Interpreter().interpret(
              new InputStreamReader(System.in), 
              "stdin"
            );
        }
        catch(Exception exception) {
            System.err.println("Exception: " + exception);
        }
    }

}
