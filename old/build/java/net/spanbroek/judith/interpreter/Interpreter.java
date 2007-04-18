package net.spanbroek.judith.interpreter;

import net.spanbroek.judith.objects.JavaObject;
import net.spanbroek.judith.objects.Text;
import net.spanbroek.judith.objects.Number;
import net.spanbroek.judith.objects.Boolean;
import net.spanbroek.judith.parser.Parser;
import net.spanbroek.judith.tree.Language;
import java.io.*;
import java.util.*;

/**
 * Represents the judith interpreter.
 *
 * @author Mark Spanbroek
 */
public class Interpreter extends Context {

    /**
     * Every thread has its own instance of the interpreter. This is basically
     * the singleton pattern, but less restricted.
     */
    static private ThreadLocal instance = new ThreadLocal() {
        protected synchronized java.lang.Object initialValue() {
            return new Interpreter();
        }
    };
    
    /**
     * Returns the instance of the interpreter that is unique for this thread.
     */
    static public Interpreter getInstance() {
        return (Interpreter)instance.get();
    }
    
    /**
     * The 'Object' judith object.
     */
    private Object object;
    
    /**
     * The 'Boolean' judith object.
     */
    private Boolean bool;
    
    /**
     * The 'Number' judith object.
     */
    private Number number;
    
    /**
     * The 'Text' judith object.
     */
    private Text text;

    /**
     * The judith object that will be used as a caller whenever the interpreter
     * itself initiates a method call.
     */
    private Object caller;

    /**
     * Returns the 'Object' judith object.
     */
    public Object getObject() {
        return object;
    }
    
    /**
     * Returns the 'Boolean' judith object.
     */
    public Boolean getBoolean() {
        return bool;
    }
    
    /**
     * Returns the 'Number' judith object.
     */
    public Number getNumber() {
        return number;
    }
    
    /**
     * Returns the 'Text' judith object.
     */
    public Text getText() {
        return text;
    }
        
    /**
     * Constructs a new interpreter. First, all objects that can't be
     * initialized in the initialize.judith script are created. Then the 
     * initialize.judith script is run.
     */    
    private Interpreter() {

        // create the primitive objects
        object = new Object();
        bool   = new Boolean(object);
        number = new Number(object);
        text   = new Text(object);
        
        // declare the primitive objects
        declare("Object", object);
        declare("Boolean", bool);
        declare("Number", number);
        declare("Text", text);
        
        // create the caller object
        caller = object.create();

        // declare the object called 'Java', which allows you to import java 
        // classes into judith
        declare("Java", new JavaObject(object));
                
    }

    /**
     * Interprets the specified parsed language.
     */    
    public void interpret(Language language) {
        
        Visitor visitor = new Visitor(this, caller);
        language.accept(visitor);
        
    }

    /**
     * Parses and interprets standard input.
     */
    static public void main(String[] arguments) throws Exception {
        
        // interpret the initialize.judith script
        getInstance().interpret(Parser.parse(new InputStreamReader(
          getInstance().getClass().getResourceAsStream("initialize.judith")
        )));
        
        // interpret the standard input
        getInstance().interpret(Parser.parse(new InputStreamReader(System.in)));
        
    }

}