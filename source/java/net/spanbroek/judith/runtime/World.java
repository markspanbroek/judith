package net.spanbroek.judith.runtime;

import net.spanbroek.judith.interpreter.Interpreter;
import java.io.*;
import java.util.List;

/**
 * Represents the global scope of Judith execution, that contains
 * the initial objects. It is implemented as a Java singleton object.
 */
public class World extends Scope {

    private Object booleanPrototype = null;
    private Object numberPrototype = null;
    private Object textPrototype = null;
    private Object listPrototype = null;
    
    /**
     * Initializes the global context of Judith execution.
     */
    public World() {

        ObjectBuilder.build(this);

        declare("self", get("Object").copy());
        declare("Objects", get("Object").copy());

        BooleanBuilder.build(this);
        NumberBuilder.build(this);
        TextBuilder.build(this);
        ExceptionBuilder.build(this);
        ExceptionHandlerBuilder.build(this);
        ListBuilder.build(this);
        DictionaryBuilder.build(this);
        ReflectionBuilder.build(this);

        interpret("Dictionary.judith");
        interpret("Exception.judith");
        interpret("world.judith");
        interpret("IO.judith");
        interpret("Reference.judith");
        interpret("List.judith");
        interpret("Counter.judith");
        interpret("parsing/Parser.judith");
        interpret("parsing/ParseResult.judith");
        interpret("parsing/CacheEntry.judith");
        interpret("parsing/Memoizer.judith");
        interpret("parsing/ParserGenerator.judith");
        interpret("parsing/ParserGenerator-empty.judith");
        interpret("parsing/ParserGenerator-begin.judith");
        interpret("parsing/ParserGenerator-end.judith");
        interpret("parsing/ParserGenerator-never.judith");
        interpret("parsing/ParserGenerator-literal.judith");
        interpret("parsing/ParserGenerator-range.judith");
        interpret("parsing/ParserGenerator-alternation.judith");
        interpret("parsing/ParserGenerator-concatenation.judith");
        interpret("parsing/ParserGenerator-optional.judith");
        interpret("parsing/ParserGenerator-not.judith");
        interpret("parsing/ParserGenerator-repetition.judith");
        interpret("parsing/ParserGenerator-repetitionplus.judith");
        interpret("parsing/ParserGenerator-rule.judith");
        interpret("parsing/ParserGenerator-transform.judith");
        interpret("parsing/ParserGenerator-asText.judith");
        interpret("parsing/ParserGenerator-memoization.judith");
        interpret("parsing/Grammar.judith");
        interpret("parsing/JudithParser.judith");
        interpret("parsing/judith/tree/Alteration.judith");
 
        IOBuilder.build(this);

    }

    /**
     * Wraps the specified boolean value as a judith Boolean object.
     */
    public Object wrap(boolean bool) {
        Object result = booleanPrototype.copy();
        result.setNativeObject(bool);
        return result;
    }

    /**
     * Wraps the specified double value as a judith Number object.
     */
    public Object wrap(double number) {
        Object result = numberPrototype.copy();
        result.setNativeObject(number);
        return result;
    }

    /**
     * Wraps the specified string as a judith Text object.
     */
    public Object wrap(String text) {
        Object result = textPrototype.copy();
        result.setNativeObject(text);
        return result;
    }

    /**
     * Wraps the specified array as a judith List object.
     */
    public Object wrap(Object[] array) {
        Object result = listPrototype.copy();
        for (Object element : array) {
            result.call("push", element);
        }
        return result;
    }

    /**
     * Unwrap judith Boolean, Number or Text object.
     */
    public java.lang.Object unwrap(Object object) {

        if (object.isCompatibleWith(booleanPrototype)) {
            return (Boolean)object.getNativeObject();
        }

        if (object.isCompatibleWith(numberPrototype)) {
            return (Double)object.getNativeObject();
        }

        if (object.isCompatibleWith(textPrototype)) {
            return (String)object.getNativeObject();
        }

        if (object.isCompatibleWith(listPrototype)) {
            return ((List<Object>)object.getNativeObject()).toArray(new Object[]{});
        }

        return null;

    }

    void setBoolean(Object booleanPrototype) {
        if (this.booleanPrototype == null) {
            this.booleanPrototype = booleanPrototype;
        }
        else {
            throw new IllegalStateException("boolean prototype already set");
        }
    }

    void setNumber(Object numberPrototype) {
        if (this.numberPrototype == null) {
            this.numberPrototype = numberPrototype;
        }
        else {
            throw new IllegalStateException("number prototype already set");
        }
    }

    void setText(Object textPrototype) {
        if (this.textPrototype == null) {
            this.textPrototype = textPrototype;
        }
        else {
            throw new IllegalStateException("text prototype already set");
        }
    }

    void setList(Object listPrototype) {
        if (this.listPrototype == null) {
            this.listPrototype = listPrototype;
        }
        else {
            throw new IllegalStateException("list prototype already set");
        }
    }
    
    private void interpret(String file) {

        try {    
            Interpreter interpreter = new Interpreter(this, this);
            InputStream in = World.class.getResourceAsStream(file);
            Reader reader = new InputStreamReader(in, "UTF-8");
            interpreter.interpret(reader, file);
        }
        catch(IOException exception) {
            throw new Error(exception);
        }

   }
}
