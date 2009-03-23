package net.spanbroek.judith.runtime;

import net.spanbroek.judith.interpreter.Interpreter;
import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Scope;
import net.spanbroek.judith.Exception;
import java.io.*;
import java.util.*;

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

        /*
         * The "Object" object.
         */
        declare("Object", new Object(this));

        // Add the Object.copy method.
        class ObjectCopyMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result",
                  scope.get("self").copy()
                );
            }
        }
        get("Object").declare("copy", new ObjectCopyMethod());

        // Add the Object.replace(object) method.
        class ObjectReplaceMethod extends Method {
            public ObjectReplaceMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.get("self").replace(scope.get("object"));
            }
        }
        get("Object").declare("replace", new ObjectReplaceMethod());

        // Add the Object.equals(object) method.
        class ObjectEqualsMethod extends Method {
            public ObjectEqualsMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  wrap(scope.get("self").equals(scope.get("object")))
                );
            }
        }
        get("Object").declare("equals", new ObjectEqualsMethod());

        // Add the Object.colon(object) method.
        class ObjectColonMethod extends Method {
            public ObjectColonMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  wrap(scope.get("self").isCompatibleWith(scope.get("object")))
                );
            }
        }
        get("Object").declare("colon", new ObjectColonMethod());


        /*
         * The Boolean object.
         */
        declare("Boolean", new Object(get("Object"), this));

        // Set the default value of the Boolean object to false.
        get("Boolean").setNativeObject(false);

        // Add the Boolean.equals(boolean) method
        class BooleanEqualsMethod extends Method {
            public BooleanEqualsMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", wrap(self == bool));
            }
        }
        get("Boolean").declare("equals", new BooleanEqualsMethod());

        // Add the Boolean.not method
        class BooleanNotMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  wrap(!(Boolean)scope.get("self").getNativeObject())
                );
            }
        }
        get("Boolean").declare("not", new BooleanNotMethod());

        // Add the Boolean.and(boolean) method
        class BooleanAndMethod extends Method {
            public BooleanAndMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", wrap(self && bool));
            }
        }
        get("Boolean").declare("and", new BooleanAndMethod());

        // Add the Boolean.or(boolean) method
        class BooleanOrMethod extends Method {
            public BooleanOrMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", wrap(self || bool));
            }
        }
        get("Boolean").declare("or", new BooleanOrMethod());


        /*
         * The Number object.
         */
        declare("Number", new Object(get("Object"), this));

        // Set the default value of Number to to 0.
        get("Number").setNativeObject(0d);

        // Add the Number.equals(number) method.
        class NumberEqualsMethod extends Method {
            public NumberEqualsMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self == number));
            }
        }
        get("Number").declare("equals", new NumberEqualsMethod());

        // Add the Number.atmost(number) method.
        class NumberAtMostMethod extends Method {
            public NumberAtMostMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self <= number));
            }
        }
        get("Number").declare("atmost", new NumberAtMostMethod());

        // Add the Number.atleast(number) method.
        class NumberAtLeastMethod extends Method {
            public NumberAtLeastMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self >= number));
            }
        }
        get("Number").declare("atleast", new NumberAtLeastMethod());

        // Add the Number.lessthan(number) method.
        class NumberLessThanMethod extends Method {
            public NumberLessThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self < number));
            }
        }
        get("Number").declare("lessthan", new NumberLessThanMethod());

        // Add the Number.morethan(number) method.
        class NumberMoreThanMethod extends Method {
            public NumberMoreThanMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self > number));
            }
        }
        get("Number").declare("morethan", new NumberMoreThanMethod());

        // Add the Number.plus(number) method.
        class NumberPlusMethod extends Method {
            public NumberPlusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self + number));
            }
        }
        get("Number").declare("plus", new NumberPlusMethod());

        // Add the Number.minus(number) method.
        class NumberMinusMethod extends Method {
            public NumberMinusMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self - number));
            }
        }
        get("Number").declare("minus", new NumberMinusMethod());

        // Add the Number.star(number) method.
        class NumberStarMethod extends Method {
            public NumberStarMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self * number));
            }
        }
        get("Number").declare("star", new NumberStarMethod());

        // Add the Number.slash(number) method.
        class NumberSlashMethod extends Method {
            public NumberSlashMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(self / number));
            }
        }
        get("Number").declare("slash", new NumberSlashMethod());

        // Add the Number.carrot(number) method.
        class NumberCarrotMethod extends Method {
            public NumberCarrotMethod() {
                super("number");
            }
            protected void execute(Scope scope) {
                double self = (Double)scope.get("self").getNativeObject();
                double number = (Double)scope.get("number").getNativeObject();
                scope.set("result", wrap(Math.pow(self,number)));
            }
        }
        get("Number").declare("carrot", new NumberCarrotMethod());

        // Add the Number.minus method.
        class NumberUnaryMinusMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  wrap(-(Double)scope.get("self").getNativeObject())
                );
            }
        }
        get("Number").declare("minus", new NumberUnaryMinusMethod());

        // Add the Number.floor method.
        class NumberFloorMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  wrap(Math.floor((Double)scope.get("self").getNativeObject()))
                );
            }
        }
        get("Number").declare("floor", new NumberFloorMethod());

        // Add the Number.ceiling method.
        class NumberCeilingMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  wrap(Math.ceil((Double)scope.get("self").getNativeObject()))
                );
            }
        }
        get("Number").declare("ceiling", new NumberCeilingMethod());


        /*
         * The Text object.
         */
        declare("Text", new Object(get("Object"), this));

        // Set the default value of Text to "".
        get("Text").setNativeObject("");

        // Add the Text.equals(text) method.
        class TextEqualsMethod extends Method {
            public TextEqualsMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", wrap(self.equals(text)));
            }
        }
        get("Text").declare("equals", new TextEqualsMethod());

        // Add the Text.plus(text) method.
        class TextPlusMethod extends Method {
            public TextPlusMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", wrap(self + text));
            }
        }
        get("Text").declare("plus", new TextPlusMethod());

        // Add the Text.excerpt(begin,end) method.
        class TextExcerptMethod extends Method {
            public TextExcerptMethod() {
                super("begin","end");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                double begin = (Double)scope.get("begin").getNativeObject();
                double end = (Double)scope.get("end").getNativeObject();
                scope.set("result", wrap(self.substring((int)begin,(int)end)));
            }
        }
        get("Text").declare("excerpt", new TextExcerptMethod());
        
        // Add the Text.quote method.
        class TextQuoteMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", wrap("\""));
            }
        }
        get("Text").declare("quote", new TextQuoteMethod());

        // Add the Text.length method.
        class TextLengthMethod extends Method {
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                scope.set("result", wrap(self.length()));
            }
        }
        get("Text").declare("length", new TextLengthMethod());


        /*
         * The Exception object.
         */
        declare("Exception", new Object(get("Object"), this));

        // Add the "message" inner object.
        Object message = get("Text").copy();
        message.setNativeObject("");
        get("Exception").declare("message", message);

        // Add the Exception.message method.
        class ExceptionMessageMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result",scope.get("message"));
            }
        }
        get("Exception").declare("message",new ExceptionMessageMethod());

        // Add the Exception.setMessage(message') method.
        class ExceptionSetMessageMethod extends Method {
            public ExceptionSetMessageMethod() {
                super("message'");
            }
            protected void execute(Scope scope) {
                scope.set("message", scope.get("message'"));
            }
        }
        get("Exception").declare("setMessage",new ExceptionSetMessageMethod());

        // Add the Exception.throw method
        class ExceptionThrowMethod extends Method {
            protected void execute(Scope scope) {
                throw new Exception(scope.get("self"));
            }
        }
        get("Exception").declare("throw",new ExceptionThrowMethod());

        /*
         * The ExceptionHandler object.
         */
        declare("ExceptionHandler", new Object(get("Object"), this));

        // Add the ExceptionHandler.run method
        class ExceptionHandlerRunMethod extends Method {
            protected void execute(Scope scope) {
                try {
                    scope.get("self").call("try");
                }
                catch(Exception exception) {
                    Object object = exception.getObject();
                    if (object != null) {
                        scope.get("self").call("catch", object);
                    }
                    else {
                        throw exception;
                    }
                }
                finally {
                    scope.get("self").call("finally");
                }
            }
        }
        get("ExceptionHandler").declare("run", new ExceptionHandlerRunMethod());
        
        /*
         * The List object.
         */
        declare("List", new Object(get("Object"), this));
        
        get("List").setNativeObject(new ArrayList<Object>());
        
        // Add the List.copy method
        class ListCopyMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                Object parent = scope.get("parent");
                Object result = parent.call("copy", new Object[]{}, self);
                List<Object> original = (List<Object>)self.getNativeObject();
                List<Object> copy = new ArrayList<Object>(original);
                result.setNativeObject(copy);
                scope.set("result", result);
            }
        }
        get("List").declare("copy", new ListCopyMethod());
        
        // Add the List.length method
        class ListLengthMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                scope.set("result", wrap(list.size()));
            }
        }
        get("List").declare("length", new ListLengthMethod());
        
        // Add the List.get(index) method
        class ListGetMethod extends Method {
            public ListGetMethod() {
                super("index");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                double index = (Double)unwrap(scope.get("index"));
                scope.set("result", list.get((int)index));
            }
        }   
        get("List").declare("get", new ListGetMethod());
        
        // Add the List.set(index, element) method
        class ListSetMethod extends Method {
            public ListSetMethod() {
                super("index", "element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                double index = (Double)unwrap(scope.get("index"));
                list.set((int)index, scope.get("element"));
            }
        }
        get("List").declare("set", new ListSetMethod());
        
        // Add the List.push(element) method
        class ListPushMethod extends Method {
            public ListPushMethod() {
                super("element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                list.add(scope.get("element"));
            }
        }
        get("List").declare("push", new ListPushMethod());
        
        // Add the List.pop() method
        class ListPopMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                scope.set("result", list.remove(list.size() - 1));
            }
        }
        get("List").declare("pop", new ListPopMethod());

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
