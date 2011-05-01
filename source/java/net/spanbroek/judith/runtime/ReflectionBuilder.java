package net.spanbroek.judith.runtime;

import java.util.List;

public class ReflectionBuilder {

    private Object reflectionToBe;
    private World world;

    public static void build(World world) {
        ReflectionBuilder builder = new ReflectionBuilder(world);
        builder.declareCallMethodWithoutArguments();
        builder.declareCallMethodWithArguments();
        builder.declareCreateProxyFor();
        builder.declareReflection();
    }

    private ReflectionBuilder(World world) {
        reflectionToBe = new Object(world.get("Object"), world);
        this.world = world;
    }

    private void declareReflection() {
        class ReflectionMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result", reflectionToBe.copy());
            }
        }
        world.get("Objects").declare("Reflection", new ReflectionMethod());
    }

    private void declareCallMethodWithoutArguments() {
        class ReflectionCallMethod extends Method {
            public ReflectionCallMethod() {
                super("object", "methodname");
            }
            protected void execute(Scope scope) {
                Object object = scope.get("object");
                String methodName = (String) scope.get("methodname").getNativeObject();
                scope.set("result", object.call(methodName));
            }
        }
        reflectionToBe.declare("call", new ReflectionCallMethod());
    }

    private void declareCallMethodWithArguments() {
        class ReflectionCallMethod extends Method {
            public ReflectionCallMethod() {
                super("object", "methodname", "arguments");
            }
            protected void execute(Scope scope) {
                Object object = scope.get("object");
                String methodName = (String) world.unwrap(scope.get("methodname"));
                Object[] arguments = (Object[]) world.unwrap(scope.get("arguments"));
                scope.set("result", object.call(methodName, arguments));
            }
        }
        reflectionToBe.declare("call", new ReflectionCallMethod());
    }

    private void declareCreateProxyFor() {
        class CreateProxyForMethod extends Method {
            public CreateProxyForMethod() {
                super("receiver");
            }
            protected void execute(Scope scope) {
                Object receiver = scope.get("receiver");
                scope.set("result", new Proxy(receiver, world));
            }
        }
        reflectionToBe.declare("createProxyFor", new CreateProxyForMethod());
    }
}