package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import net.spanbroek.judith.Exception;

class ExceptionHandlerBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        ExceptionHandlerBuilder builder = new ExceptionHandlerBuilder(scope);
        builder.declareExceptionHandler();
        builder.declareRunMethod();
    }

    private ExceptionHandlerBuilder(Scope scope) {
        this.scope = scope;
    }

    private void declareExceptionHandler() {
        scope.declare(
          "ExceptionHandler", 
          new Object(scope.get("Object"), scope)
        );
    }
    
    private void declareRunMethod() {
        class ExceptionHandlerRunMethod extends Method {
            protected void execute(Scope scope) {
                try {
                    scope.get("self").call("try");
                }
                catch(Exception exception) {
                    Object object = exception.getObject();
                    if (object != null) {
                        scope.get("self").call("catch", object);
                    }
                    else {
                        throw exception;
                    }
                }
                finally {
                    scope.get("self").call("finally");
                }
            }
        }
        scope.get("ExceptionHandler").declare(
          "run", 
          new ExceptionHandlerRunMethod()
        );
    }

}
