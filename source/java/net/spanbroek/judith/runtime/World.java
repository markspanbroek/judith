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
     * Indicates whether world.judith has been executed.
     */
    private static boolean initialized = false;
    
    /**
     * Initializes the global context of Judith execution.
     */
    public World() {

        ObjectBuilder.build(this);

        declare("self", get("Object").copy());

        BooleanBuilder.build(this);
        NumberBuilder.build(this);
        TextBuilder.build(this);
        ExceptionBuilder.build(this);
        ExceptionHandlerBuilder.build(this);
        ListBuilder.build(this);

        interpret("world.judith");
        interpret("IO.judith");
        interpret("Reference.judith");
        interpret("parsing/Parser.judith");
        interpret("parsing/basics.judith");
        interpret("parsing/astext.judith");
        interpret("parsing/memoization.judith");
 
        IOBuilder.build(this);

    }

    /**
     * Wraps the specified boolean value as a judith Boolean object.
     */
    public Object wrap(boolean bool) {
        Object result = get("Boolean").copy();
        result.setNativeObject(bool);
        return result;
    }

    /**
     * Wraps the specified double value as a judith Number object.
     */
    public Object wrap(double number) {
        Object result = get("Number").copy();
        result.setNativeObject(number);
        return result;
    }

    /**
     * Wraps the specified string as a judith Text object.
     */
    public Object wrap(String text) {
        Object result = get("Text").copy();
        result.setNativeObject(text);
        return result;
    }

    /**
     * Unwrap judith Boolean, Number or Text object.
     */
    public java.lang.Object unwrap(Object object) {

        if (object.isCompatibleWith(get("Boolean"))) {
            return (Boolean)object.getNativeObject();
        }

        if (object.isCompatibleWith(get("Number"))) {
            return (Double)object.getNativeObject();
        }

        if (object.isCompatibleWith(get("Text"))) {
            return (String)object.getNativeObject();
        }

        return null;

    }
    
    private void interpret(String file) {

        try {    
            Interpreter interpreter = new Interpreter(this);
            InputStream in = World.class.getResourceAsStream(file);
            Reader reader = new InputStreamReader(in, "UTF-8");
            interpreter.interpret(reader, file);
        }
        catch(IOException exception) {
            throw new Error(exception);
        }

   }

}
