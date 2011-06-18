package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.parser.Parser;
import net.spanbroek.judith.tree.*;
import net.spanbroek.judith.runtime.*;
import net.spanbroek.judith.Exception;
import java.io.*;

public class Interpreter {

    protected World world;
    protected Scope scope;

    public Interpreter() {
        this(new World());
    }

    public Interpreter(World world) {
        this(world, new Scope(world));
    }

    public Interpreter(World world, Scope scope) {
        this.world = world;
        this.scope = scope;
    }

    public void interpret(Statement... statements) {
        new Visitor(world, scope).visit(statements);
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

    public static void main(String[] arguments) throws IOException, Exception {
    
        Interpreter interpreter = new Interpreter();
        
        try {
            if (arguments.length > 0) {
                for(String file : arguments) {
                    interpreter.interpret(new FileReader(file), file);
                }
            }
            else {
                interpreter.interpret(
                  new InputStreamReader(System.in), 
                  "stdin"
                );
            }        
        }
        catch(Exception exception) {
            System.err.println("Exception: " + exception);
            System.exit(1);
        }
        
    }

}
