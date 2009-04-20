package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;

class ObjectBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        ObjectBuilder builder = new ObjectBuilder(scope);
        builder.declareObject();
        builder.declareCopyMethod();
        builder.declareReplaceMethod();
        builder.declareEqualsMethod();
        builder.declareColonMethod();
    }

    private ObjectBuilder(Scope scope) {
        this.scope = scope;
    }

    public void declareObject() {
        scope.declare("Object", new Object(scope));
    }

    public void declareCopyMethod() {
        class ObjectCopyMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result",
                  scope.get("self").copy()
                );
            }
        }
        scope.get("Object").declare("copy", new ObjectCopyMethod());
    }
    
    public void declareReplaceMethod() {
        class ObjectReplaceMethod extends Method {
            public ObjectReplaceMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.get("self").replace(scope.get("object"));
            }
        }
        scope.get("Object").declare("replace", new ObjectReplaceMethod());
    }
    
    public void declareEqualsMethod() {
        class ObjectEqualsMethod extends Method {
            public ObjectEqualsMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  World.wrap(scope.get("self").equals(scope.get("object")))
                );
            }
        }
        scope.get("Object").declare("equals", new ObjectEqualsMethod());
    }
    
    public void declareColonMethod() {
        class ObjectColonMethod extends Method {
            public ObjectColonMethod() {
                super("object");
            }
            protected void execute(Scope scope) {
                scope.set("result",
                  World.wrap(scope.get("self").isCompatibleWith(scope.get("object")))
                );
            }
        }
        scope.get("Object").declare("colon", new ObjectColonMethod());
    }

}
