package net.spanbroek.judith.runtime;

import net.spanbroek.judith.interpreter.Interpreter;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Scope;
import net.spanbroek.judith.Exception;
import java.io.*;

/**
 * Represents the global scope of Judith execution, that contains
 * the initial objects. It is implemented as a Java singleton object.
 */
public class World extends Scope {

    /**
     * The singleton instance of this object.
     */
    private static World INSTANCE = new World();

    /**
     * Indicates whether world.judith has been executed.
     */
    private static boolean initialized = false;
    
    /**
     * Initializes the global context of Judith execution.
     */
    private World() {

        ObjectBuilder.build(this);
        BooleanBuilder.build(this);
        NumberBuilder.build(this);
        TextBuilder.build(this);
        ExceptionBuilder.build(this);
        ExceptionHandlerBuilder.build(this);
        ListBuilder.build(this);

        // Add a 'self' object
        declare("self", get("Object").copy());

    }

    /**
     * Returns the singleton instance.
     */
    public static synchronized World getInstance() {
        if (!initialized) {
            initialized = true;
            try {
 
                new Interpreter(INSTANCE).interpret(
                  new InputStreamReader(
                    World.class.getResourceAsStream("world.judith"),
                    "UTF-8"
                  ),
                  "world.judith"
                );
 
                new Interpreter(INSTANCE).interpret(
                  new InputStreamReader(
                    World.class.getResourceAsStream("IO.judith"),
                    "UTF-8"
                  ),
                  "IO.judith"
                );
 
 
                class WriteMethod extends Method {
                
                    public WriteMethod() {
                        super("text");
                    }
                    
                    protected void execute(Scope scope) {
                        // TODO: something goes wrong here, when using 'parent' and 'replace'
                        //scope.get("parent").call("write", new Object[]{scope.get("text")}, scope.get("self"));
                        System.out.print((String)unwrap(scope.get("text")));
                        System.out.flush();
                    }
                }
                
                class ReadLineMethod extends Method {
                    protected void execute(Scope scope) {
                        try {
                            scope.set("result", wrap(new BufferedReader(new InputStreamReader(System.in)).readLine()));
                        }
                        catch(IOException exception) {
                            throw new Exception(exception.getMessage());
                        }
                    }
                }

                Scope scope = INSTANCE;
         
                Object io = scope.get("Objects").call("IO");
                Object standardOutput = io.call("StandardOutput");
                Object standardInput  = io.call("StandardInput" );
                
                Object newStandardOutput = new Object(standardOutput, scope);
                Object newStandardInput  = new Object(standardInput,  scope);
                
                newStandardOutput.declare("write", new WriteMethod());
                newStandardInput.declare("readLine", new ReadLineMethod());
                
                standardOutput.replace(newStandardOutput);
                standardInput.replace(newStandardInput);                
                
             }
            catch(IOException exception) {
                throw new Error(exception);
            }
        }
        return INSTANCE;
    }

    /**
     * Wraps the specified boolean value as a judith Boolean object.
     */
    static public Object wrap(boolean bool) {
        Object result = getInstance().get("Boolean").copy();
        result.setNativeObject(bool);
        return result;
    }

    /**
     * Wraps the specified double value as a judith Number object.
     */
    static public Object wrap(double number) {
        Object result = getInstance().get("Number").copy();
        result.setNativeObject(number);
        return result;
    }

    /**
     * Wraps the specified string as a judith Text object.
     */
    static public Object wrap(String text) {
        Object result = getInstance().get("Text").copy();
        result.setNativeObject(text);
        return result;
    }

    /**
     * Unwrap judith Boolean, Number or Text object.
     */
    static public java.lang.Object unwrap(Object object) {

        if (object.isCompatibleWith(getInstance().get("Boolean"))) {
            return (Boolean)object.getNativeObject();
        }

        if (object.isCompatibleWith(getInstance().get("Number"))) {
            return (Double)object.getNativeObject();
        }

        if (object.isCompatibleWith(getInstance().get("Text"))) {
            return (String)object.getNativeObject();
        }

        return null;

    }

}
