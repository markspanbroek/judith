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
                super("equals", "text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", world.wrap(self.equals(text)));
            }
        }
        textToBe.declare(new TextEqualsMethod());
    }

    private void declarePlusMethod() {
        class TextPlusMethod extends Method {
            public TextPlusMethod() {
                super("plus", "text");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String text = (String)scope.get("text").getNativeObject();
                scope.set("result", world.wrap(self + text));
            }
        }
        textToBe.declare(new TextPlusMethod());
    }

    private void declareExcerptMethod() {
        class TextExcerptMethod extends Method {
            public TextExcerptMethod() {
                super("excerpt", "begin","end");
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
        textToBe.declare(new TextExcerptMethod());
    }

    private void declareQuoteMethod() {
        class TextQuoteMethod extends Method {
            public TextQuoteMethod() {
                super("quote");
            }
            protected void execute(Scope scope) {
                scope.set("result", world.wrap("\""));
            }
        }
        textToBe.declare(new TextQuoteMethod());
    }

    private void declareLineEndMethod() {
        class LineEndMethod extends Method {
            public LineEndMethod() {
                super("lineEnd");
            }
            protected void execute(Scope scope) {
                scope.set("result", world.wrap("\n"));
            }
        }
        textToBe.declare(new LineEndMethod());
    }

    private void declareLengthMethod() {
        class TextLengthMethod extends Method {
            public TextLengthMethod() {
                super("length");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                scope.set("result", world.wrap(self.length()));
            }
        }
        textToBe.declare(new TextLengthMethod());
    }

    private void declareAtmostMethod() {
        class TextAtmostMethod extends Method {
            public TextAtmostMethod() {
                super("atmost", "other");
            }
            protected void execute(Scope scope) {
                String self = (String)scope.get("self").getNativeObject();
                String other = (String)scope.get("other").getNativeObject();
                scope.set("result", world.wrap(self.compareTo(other) <= 0));
            }
        }
        textToBe.declare(new TextAtmostMethod());
    }

    private void declareText() {
        class TextMethod extends Method {
            public TextMethod() {
                super("Text");
            }
            protected void execute(Scope scope) {
                scope.set("result", textToBe.copy());
            }
        }
        world.get("Objects").declare(new TextMethod());
        world.setText(textToBe);
    }

}
