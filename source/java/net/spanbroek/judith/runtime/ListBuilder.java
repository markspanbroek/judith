package net.spanbroek.judith.runtime;

import java.util.*;
import java.util.List;

class ListBuilder {

    private World world;

    public static void build(World world) {
        ListBuilder builder = new ListBuilder(world);
        builder.declareList();
        builder.declareCopyMethod();
        builder.declareLengthMethod();
        builder.declareGetMethod();
        builder.declareSetMethod();
        builder.declarePushMethod();
        builder.declareRemoveMethod();
    }

    private ListBuilder(World world) {
        this.world = world;
    }

    private void declareList() {
        world.declare("List", new Object(world.get("Object"), world));
        world.get("List").setNativeObject(new ArrayList<Object>());
        world.setList(world.get("List"));
    }

    private void declareCopyMethod() {
        class ListCopyMethod extends Method {
            public ListCopyMethod() {
                super("copy");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                Object parent = scope.get("parent");
                Object result = parent.call("copy", new Object[]{});
                List<Object> original = self.getNativeObject();
                List<Object> copy = new ArrayList<Object>(original);
                result.setNativeObject(copy);
                scope.set("result", result);
            }
        }
        world.get("List").declare(new ListCopyMethod());
    }

    private void declareLengthMethod() {
        class ListLengthMethod extends Method {
            public ListLengthMethod() {
                super("length");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = self.getNativeObject();
                scope.set("result", world.wrap(list.size()));
            }
        }
        world.get("List").declare(new ListLengthMethod());
    }

    private void declareGetMethod() {
        class ListGetMethod extends Method {
            public ListGetMethod() {
                super("get", "index");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = self.getNativeObject();
                double index = (Double)world.unwrap(scope.get("index"));
                scope.set("result", list.get((int)index));
            }
        }
        world.get("List").declare(new ListGetMethod());
    }

    private void declareSetMethod() {
        class ListSetMethod extends Method {
            public ListSetMethod() {
                super("set", "index", "element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = self.getNativeObject();
                double index = (Double)world.unwrap(scope.get("index"));
                list.set((int)index, scope.get("element"));
            }
        }
        world.get("List").declare(new ListSetMethod());
    }

    private void declarePushMethod() {
        class ListPushMethod extends Method {
            public ListPushMethod() {
                super("push", "element");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                List<Object> list = self.getNativeObject();
                list.add(scope.get("element"));
            }
        }
        world.get("List").declare(new ListPushMethod());
    }

    private void declareRemoveMethod() {
        class ListRemoveMethod extends Method {
            public ListRemoveMethod() {
                super("remove", "index");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                double index = (Double)world.unwrap(scope.get("index"));
                List<Object> list = self.getNativeObject();
                scope.set("result", list.remove((int)index));
            }
        }
        world.get("List").declare(new ListRemoveMethod());
    }

}
