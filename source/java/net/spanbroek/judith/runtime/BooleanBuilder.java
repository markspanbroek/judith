package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class BooleanBuilder {

    private World world;
    
    public static void build(World world) {
        BooleanBuilder builder = new BooleanBuilder(world);
        builder.declareBoolean();
        builder.declareEqualsMethod();
        builder.declareNotMethod();
        builder.declareAndMethod();
        builder.declareOrMethod();
    }

    private BooleanBuilder(World world) {
        this.world = world;
    }
    
    private void declareBoolean() {
        world.declare("Boolean", new Object(world.get("Object"), world));
        world.get("Boolean").setNativeObject(false);
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
        world.get("Boolean").declare("equals", new BooleanEqualsMethod());
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
        world.get("Boolean").declare("not", new BooleanNotMethod());
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
        world.get("Boolean").declare("and", new BooleanAndMethod());
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
        world.get("Boolean").declare("or", new BooleanOrMethod());
    }

}
