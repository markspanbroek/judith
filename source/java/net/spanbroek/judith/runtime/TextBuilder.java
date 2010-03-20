package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class TextBuilder {

    private World world;

    private Object textToBe;
    
    public static void build(World world) {
        TextBuilder builder = new TextBuilder(world);
        builder.declareEqualsMethod();
        builder.declarePlusMethod();
        builder.declareExcerptMethod();
        builder.declareQuoteMethod();
        builder.declareLineEndMethod();
        builder.declareLengthMethod();
        builder.declareAtmostMethod();
        builder.declareText();
    }

    private TextBuilder(World world) {
        this.world = world;
        textToBe = new Object(world.get("Object"), world);
        textToBe.setNativeObject("");
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
        textToBe.declare("equals", new TextEqualsMethod());
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
        textToBe.declare("plus", new TextPlusMethod());
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
        textToBe.declare("excerpt", new TextExcerptMethod());
    }
    
    private void declareQuoteMethod() {
        class TextQuoteMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", world.wrap("\""));
            }
        }
        textToBe.declare("quote", new TextQuoteMethod());
    }
    
    private void declareLineEndMethod() {
        class LineEndMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", world.wrap("\n"));
            }
        }
        textToBe.declare("lineEnd", new LineEndMethod());
    }

    private void declareLengthMethod() {
        class TextLengthMethod extends Method {
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                scope.set("result", world.wrap(self.length()));
            }
        }
        textToBe.declare("length", new TextLengthMethod());
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
        textToBe.declare("atmost", new TextAtmostMethod());
    }

    private void declareText() {
        class TextMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", textToBe.copy());
            }
        }
        world.get("Objects").declare("Text", new TextMethod());
        world.setText(textToBe);
    }

}
