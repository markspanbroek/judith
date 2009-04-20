package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Object;
import java.util.*;

class ListBuilder {

    private Scope scope;
    
    public static void build(Scope scope) {
        ListBuilder builder = new ListBuilder(scope);
        builder.declareList();
        builder.declareCopyMethod();
        builder.declareLengthMethod();
        builder.declareGetMethod();
        builder.declareSetMethod();
        builder.declarePushMethod();
        builder.declarePopMethod();
    }

    private ListBuilder(Scope scope) {
        this.scope = scope;
    }
    
    private void declareList() {
        scope.declare("List", new Object(scope.get("Object"), scope));
        scope.get("List").setNativeObject(new ArrayList<Object>());
    }
    
    private void declareCopyMethod() {
        class ListCopyMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                Object parent = scope.get("parent");
                Object result = parent.call("copy", new Object[]{}, self);
                List<Object> original = (List<Object>)self.getNativeObject();
                List<Object> copy = new ArrayList<Object>(original);
                result.setNativeObject(copy);
                scope.set("result", result);
            }
        }
        scope.get("List").declare("copy", new ListCopyMethod());
    }
        
    private void declareLengthMethod() {
        class ListLengthMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                scope.set("result", World.wrap(list.size()));
            }
        }
        scope.get("List").declare("length", new ListLengthMethod());
    }
    
    private void declareGetMethod() {
        class ListGetMethod extends Method {
            public ListGetMethod() {
                super("index");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                double index = (Double)World.unwrap(scope.get("index"));
                scope.set("result", list.get((int)index));
            }
        }   
        scope.get("List").declare("get", new ListGetMethod());
    }
    
    private void declareSetMethod() {
        class ListSetMethod extends Method {
            public ListSetMethod() {
                super("index", "element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                double index = (Double)World.unwrap(scope.get("index"));
                list.set((int)index, scope.get("element"));
            }
        }
        scope.get("List").declare("set", new ListSetMethod());
    }
    
    private void declarePushMethod() {
        class ListPushMethod extends Method {
            public ListPushMethod() {
                super("element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                list.add(scope.get("element"));
            }
        }
        scope.get("List").declare("push", new ListPushMethod());
    }
    
    private void declarePopMethod() {
        class ListPopMethod extends Method {
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = (List<Object>)self.getNativeObject();
                scope.set("result", list.remove(list.size() - 1));
            }
        }
        scope.get("List").declare("pop", new ListPopMethod());
    }

}
