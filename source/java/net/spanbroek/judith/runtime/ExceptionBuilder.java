package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

class ExceptionBuilder {

    private World world;

    public static void build(World scope) {
        ExceptionBuilder builder = new ExceptionBuilder(scope);
        builder.declareException();
        builder.declareMessageMethod();
        builder.declareSetMessageMethod();
        builder.declareThrowMethod();
    }

    private ExceptionBuilder(World scope) {
        this.world = scope;
    }

    private void declareException() {
        world.declare("Exception", new Object(world.get("Object"), world));
        world.get("Exception").declare("message", world.wrap(""));
    }

    private void declareMessageMethod() {
        class ExceptionMessageMethod extends Method {
            public ExceptionMessageMethod()
            {
                super("message");
            }
            protected void execute(Scope scope) {
                scope.set("result",scope.get("message"));
            }
        }
        world.get("Exception").declare(new ExceptionMessageMethod());
    }

    private void declareSetMessageMethod() {
        class ExceptionSetMessageMethod extends Method {
            public ExceptionSetMessageMethod() {
                super("setMessage", "message'");
            }
            protected void execute(Scope scope) {
                scope.set("message", scope.get("message'"));
            }
        }
        world.get("Exception").declare(
          new ExceptionSetMessageMethod()
        );
    }

    private void declareThrowMethod() {
        class ExceptionThrowMethod extends Method {
            public ExceptionThrowMethod() {
                super("throw");
            }
            protected void execute(Scope scope) {
                throw new Exception(scope.get("self"));
            }
        }
        world.get("Exception").declare(new ExceptionThrowMethod());
    }

}
