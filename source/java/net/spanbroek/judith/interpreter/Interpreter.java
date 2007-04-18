package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.parser.Parser;
import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.runtime.*;
import java.io.*;

public class Interpreter {

    protected Scope scope;

    public Interpreter(Scope scope) {
        this.scope = scope;
    }

    public Interpreter() {
        this(new CopyOnWriteScope(World.getInstance()));
    }

    public void interpret(Statement... statements) {
        new Visitor(scope, World.getInstance().get("self")).visit(statements);
    }

    public void interpret(Program program) {
        interpret(program.getStatements());
    }

    public void interpret(Reader reader) throws IOException {
        Program program = Parser.parse(reader);
        interpret(program);
    }

    public void interpret(String string) {
        try {
            interpret(new StringReader(string));
        }
        catch(IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void main(String[] arguments) throws IOException {
        new Interpreter().interpret(new InputStreamReader(System.in));
    }

}