package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class TextBuilder {

    private World world;
    
    public static void build(World world) {
        TextBuilder builder = new TextBuilder(world);
        builder.declareText();
        builder.declareEqualsMethod();
        builder.declarePlusMethod();
        builder.declareExcerptMethod();
        builder.declareQuoteMethod();
        builder.declareLengthMethod();
        builder.declareAtmostMethod();
    }

    private TextBuilder(World world) {
        this.world = world;
    }
    
    private void declareText() {
        world.declare("Text", new Object(world.get("Object"), world));
        world.get("Text").setNativeObject("");
    }
    
    private void declareEqualsMethod() {
        class TextEqualsMethod extends Method {
            public TextEqualsMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", world.wrap(self.equals(text)));
            }
        }
        world.get("Text").declare("equals", new TextEqualsMethod());
    }
    
    private void declarePlusMethod() {
        class TextPlusMethod extends Method {
            public TextPlusMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", world.wrap(self + text));
            }
        }
        world.get("Text").declare("plus", new TextPlusMethod());
    }
    
    private void declareExcerptMethod() {
        class TextExcerptMethod extends Method {
            public TextExcerptMethod() {
                super("begin","end");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                double begin = (Double)scope.get("begin").getNativeObject();
                double end = (Double)scope.get("end").getNativeObject();
                scope.set(
                  "result", 
                  world.wrap(self.substring((int)begin,(int)end))
                );
            }
        }
        world.get("Text").declare("excerpt", new TextExcerptMethod());
    }
    
    private void declareQuoteMethod() {
        class TextQuoteMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", world.wrap("\""));
            }
        }
        world.get("Text").declare("quote", new TextQuoteMethod());
    }
    
    private void declareLengthMethod() {
        class TextLengthMethod extends Method {
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                scope.set("result", world.wrap(self.length()));
            }
        }
        world.get("Text").declare("length", new TextLengthMethod());    
    }
    
    private void declareAtmostMethod() {
        class TextAtmostMethod extends Method {
            public TextAtmostMethod() {
                super("other");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String other = (String)scope.get("other").getNativeObject();
                scope.set("result", world.wrap(self.compareTo(other) <= 0));
            }
        }
        world.get("Text").declare("atmost", new TextAtmostMethod());
    }

}
