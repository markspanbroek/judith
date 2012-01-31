package net.spanbroek.judith.runtime;

class ObjectBuilder {

    private World world;

    public static void build(World world) {
        ObjectBuilder builder = new ObjectBuilder(world);
        builder.declareObject();
        builder.declareCopyMethod();
        builder.declareReplaceMethod();
        builder.declareEqualsMethod();
        builder.declareColonMethod();
    }

    private ObjectBuilder(World world) {
        this.world = world;
    }

    public void declareObject() {
        world.declare("Object", new Object(world));
    }

    public void declareCopyMethod() {
        class ObjectCopyMethod extends Method {
            public ObjectCopyMethod() {
                super("copy");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  scope.get("self").copy()
                );
            }
        }
        world.get("Object").declare(new ObjectCopyMethod());
    }

    public void declareReplaceMethod() {
        class ObjectReplaceMethod extends Method {
            public ObjectReplaceMethod() {
                super("replace", "object");
            }
            protected void execute(Scope scope) {
                scope.get("self").replace(scope.get("object"));
            }
        }
        world.get("Object").declare(new ObjectReplaceMethod());
    }

    public void declareEqualsMethod() {
        class ObjectEqualsMethod extends Method {
            public ObjectEqualsMethod() {
                super("equals", "object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  world.wrap(scope.get("self").equals(scope.get("object")))
                );
            }
        }
        world.get("Object").declare(new ObjectEqualsMethod());
    }

    public void declareColonMethod() {
        class ObjectColonMethod extends Method {
            public ObjectColonMethod() {
                super("colon", "object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  world.wrap(scope.get("self").isCompatibleWith(scope.get("object")))
                );
            }
        }
        world.get("Object").declare(new ObjectColonMethod());
    }

}
