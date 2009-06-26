package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class BooleanBuilder {

    private World world;
    private Object wannabeBoolean;
    
    public static void build(World world) {
        BooleanBuilder builder = new BooleanBuilder(world);
        builder.declareEqualsMethod();
        builder.declareNotMethod();
        builder.declareAndMethod();
        builder.declareOrMethod();
        builder.declareBoolean();
    }

    private BooleanBuilder(World world) {
        this.world = world;
        wannabeBoolean = new Object(world.get("Object"), world);
        wannabeBoolean.setNativeObject(false);
    }
    
    private void declareEqualsMethod() {
        class BooleanEqualsMethod extends Method {
            public BooleanEqualsMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self == bool));
            }
        }
        wannabeBoolean.declare("equals", new BooleanEqualsMethod());
    }
    
    private void declareNotMethod() {
        class BooleanNotMethod extends Method {
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(!(Boolean)scope.get("self").getNativeObject())
                );
            }
        }
        wannabeBoolean.declare("not", new BooleanNotMethod());
    }
    
    private void declareAndMethod() {
        class BooleanAndMethod extends Method {
            public BooleanAndMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self && bool));
            }
        }
        wannabeBoolean.declare("and", new BooleanAndMethod());
    }
    
    private void declareOrMethod() {
        class BooleanOrMethod extends Method {
            public BooleanOrMethod() {
                super("boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self || bool));
            }
        }
        wannabeBoolean.declare("or", new BooleanOrMethod());
    }

    private void declareBoolean() {
        class BooleanMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", wannabeBoolean);
            }
        }
        world.get("Objects").declare("Boolean", new BooleanMethod());
        world.declare("Boolean", wannabeBoolean);
    }    
}
