package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

class ExceptionBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        ExceptionBuilder builder = new ExceptionBuilder(scope);
        builder.declareException();
        builder.declareMessageMethod();
        builder.declareSetMessageMethod();
        builder.declareThrowMethod();
    }

    private ExceptionBuilder(Scope scope) {
        this.scope = scope;
    }

    private void declareException() {
        scope.declare("Exception", new Object(scope.get("Object"), scope));
        Object message = scope.get("Text").copy();
        message.setNativeObject("");
        scope.get("Exception").declare("message", message);
    }
    
    private void declareMessageMethod() {
        class ExceptionMessageMethod extends Method {
            protected void execute(Scope scope) {
                scope.set("result",scope.get("message"));
            }
        }
        scope.get("Exception").declare("message",new ExceptionMessageMethod());
    }

    private void declareSetMessageMethod() {
        class ExceptionSetMessageMethod extends Method {
            public ExceptionSetMessageMethod() {
                super("message'");
            }
            protected void execute(Scope scope) {
                scope.set("message", scope.get("message'"));
            }
        }
        scope.get("Exception").declare(
          "setMessage",
          new ExceptionSetMessageMethod()
        );
    }
    
    private void declareThrowMethod() {
        class ExceptionThrowMethod extends Method {
            protected void execute(Scope scope) {
                throw new Exception(scope.get("self"));
            }
        }
        scope.get("Exception").declare("throw",new ExceptionThrowMethod());
    }

}
