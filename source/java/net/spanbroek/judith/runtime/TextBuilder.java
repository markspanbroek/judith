package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class TextBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        TextBuilder builder = new TextBuilder(scope);
        builder.declareText();
        builder.declareEqualsMethod();
        builder.declarePlusMethod();
        builder.declareExcerptMethod();
        builder.declareQuoteMethod();
        builder.declareLengthMethod();
    }

    private TextBuilder(Scope scope) {
        this.scope = scope;
    }
    
    private void declareText() {
        scope.declare("Text", new Object(scope.get("Object"), scope));
        scope.get("Text").setNativeObject("");
    }
    
    private void declareEqualsMethod() {
        class TextEqualsMethod extends Method {
            public TextEqualsMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", World.wrap(self.equals(text)));
            }
        }
        scope.get("Text").declare("equals", new TextEqualsMethod());
    }
    
    private void declarePlusMethod() {
        class TextPlusMethod extends Method {
            public TextPlusMethod() {
                super("text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", World.wrap(self + text));
            }
        }
        scope.get("Text").declare("plus", new TextPlusMethod());
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
                  World.wrap(self.substring((int)begin,(int)end))
                );
            }
        }
        scope.get("Text").declare("excerpt", new TextExcerptMethod());
    }
    
    private void declareQuoteMethod() {
        class TextQuoteMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", World.wrap("\""));
            }
        }
        scope.get("Text").declare("quote", new TextQuoteMethod());
    }
    
    private void declareLengthMethod() {
        class TextLengthMethod extends Method {
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                scope.set("result", World.wrap(self.length()));
            }
        }
        scope.get("Text").declare("length", new TextLengthMethod());    
    }

}
