package net.spanbroek.judith.runtime;

import java.util.List;

public class ReflectionBuilder {

    private Object reflectionToBe;
    private World world;

    public static void build(World world) {
        ReflectionBuilder builder = new ReflectionBuilder(world);
        builder.declareCallMethodWithoutArguments();
        builder.declareCallMethodWithArguments();
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
                String methodName = (String) scope.get("methodname").getNativeObject();
                Object[] arguments = ((List<Object>)scope.get("arguments").getNativeObject()).toArray(new Object[]{});
                scope.set("result", object.call(methodName, arguments));
            }
        }
        reflectionToBe.declare("call", new ReflectionCallMethod());
    }
}