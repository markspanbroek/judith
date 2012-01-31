package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class BooleanBuilder {

    private World world;
    private Object booleanToBe;

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
        booleanToBe = new Object(world.get("Object"), world);
        booleanToBe.setNativeObject(false);
    }

    private void declareEqualsMethod() {
        class BooleanEqualsMethod extends Method {
            public BooleanEqualsMethod() {
                super("equals", "boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self == bool));
            }
        }
        booleanToBe.declare(new BooleanEqualsMethod());
    }

    private void declareNotMethod() {
        class BooleanNotMethod extends Method {
            public BooleanNotMethod() {
                super("not");
            }
            protected void execute(Scope scope) {
                scope.set(
                  "result",
                  world.wrap(!(Boolean)scope.get("self").getNativeObject())
                );
            }
        }
        booleanToBe.declare(new BooleanNotMethod());
    }

    private void declareAndMethod() {
        class BooleanAndMethod extends Method {
            public BooleanAndMethod() {
                super("and", "boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self && bool));
            }
        }
        booleanToBe.declare(new BooleanAndMethod());
    }

    private void declareOrMethod() {
        class BooleanOrMethod extends Method {
            public BooleanOrMethod() {
                super("or", "boolean");
            }
            protected void execute(Scope scope) {
                boolean self = (Boolean)scope.get("self").getNativeObject();
                boolean bool = (Boolean)scope.get("boolean").getNativeObject();
                scope.set("result", world.wrap(self || bool));
            }
        }
        booleanToBe.declare(new BooleanOrMethod());
    }

    private void declareBoolean() {
        class BooleanMethod extends Method {
            public BooleanMethod() {
                super("Boolean");
            }
            protected void execute(Scope scope) {
                scope.set("result", booleanToBe.copy());
            }
        }
        world.get("Objects").declare(new BooleanMethod());
        world.setBoolean(booleanToBe);
    }
}
