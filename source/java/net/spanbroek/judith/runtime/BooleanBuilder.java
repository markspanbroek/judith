package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class BooleanBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        BooleanBuilder builder = new BooleanBuilder(scope);
        builder.declareBoolean();
        builder.declareEqualsMethod();
        builder.declareNotMethod();
        builder.declareAndMethod();
        builder.declareOrMethod();
    }

    private BooleanBuilder(Scope scope) {
        this.scope = scope;
    }
    
    private void declareBoolean() {
        scope.declare("Boolean", new Object(scope.get("Object"), scope));
        scope.get("Boolean").setNativeObject(false);
    }
    
    private void declareEqualsMethod() {
        class BooleanEqualsMethod extends Method {
            public BooleanEqualsMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", World.wrap(self == bool));
            }
        }
        scope.get("Boolean").declare("equals", new BooleanEqualsMethod());
    }
    
    private void declareNotMethod() {
        class BooleanNotMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  World.wrap(!(Boolean)scope.get("self").getNativeObject())
                );
            }
        }
        scope.get("Boolean").declare("not", new BooleanNotMethod());
    }
    
    private void declareAndMethod() {
        class BooleanAndMethod extends Method {
            public BooleanAndMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", World.wrap(self && bool));
            }
        }
        scope.get("Boolean").declare("and", new BooleanAndMethod());
    }
    
    private void declareOrMethod() {
        class BooleanOrMethod extends Method {
            public BooleanOrMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", World.wrap(self || bool));
            }
        }
        scope.get("Boolean").declare("or", new BooleanOrMethod());
    }

}
