package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.runtime.Scope;
import net.spanbroek.judith.Exception;

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
        get("Number").setNativeObject(0);

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

        // Add the Exception.getMessage method.
        class ExceptionGetMessageMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result",scope.get("message"));
            }
        }
        get("Exception").declare("getMessage",new ExceptionGetMessageMethod());

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

        // Add a 'self' object
        declare("self", get("Object").copy());

    }

    /**
     * Returns the singleton instance.
     */
    public static World getInstance() {
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