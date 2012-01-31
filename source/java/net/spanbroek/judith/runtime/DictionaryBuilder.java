package net.spanbroek.judith.runtime;

import java.util.HashMap;

class DictionaryBuilder {

    private World world;

    private Object dictionaryToBe;

    public static void build(World world) {
        DictionaryBuilder builder = new DictionaryBuilder(world);
        builder.declareHasMethod();
        builder.declareGetMethod();
        builder.declareSetMethod();
        builder.declareCopyMethod();
        builder.declareDictionary();
    }

    private DictionaryBuilder(World world) {
        this.world = world;
        dictionaryToBe = new Object(world.get("Object"), world);
        dictionaryToBe.setNativeObject(new HashMap<String,Object>());
    }

    private void declareHasMethod() {
        class HasMethod extends Method {
            public HasMethod() {
                super("has", "key");
            }

            @Override
            protected void execute(Scope scope) {
                HashMap<String, Object> map;
                map = scope.get("self").getNativeObject();
                String key = scope.get("key").getNativeObject();
                scope.set("result", world.wrap(map.containsKey(key)));
            }
        }
        dictionaryToBe.declare(new HasMethod());
    }

    private void declareGetMethod() {
        class GetMethod extends Method {
            public GetMethod() {
                super("get", "key");
            }

            @Override
            protected void execute(Scope scope) {
                HashMap<String, Object> map;
                map = scope.get("self").getNativeObject();
                String key = scope.get("key").getNativeObject();
                scope.set("result", map.get(key));
            }
        }
        dictionaryToBe.declare(new GetMethod());
    }

    private void declareSetMethod() {
        class SetMethod extends Method {
            public SetMethod() {
                super("set", "key", "value");
            }

            @Override
            protected void execute(Scope scope) {
                HashMap<String, Object> map;
                map = scope.get("self").getNativeObject();
                String key = scope.get("key").getNativeObject();
                map.put(key, scope.get("value"));
            }
        }
        dictionaryToBe.declare(new SetMethod());
    }

    private void declareCopyMethod() {
        class CopyMethod extends Method {
            public CopyMethod() {
                super("copy");
            }
            protected void execute(Scope scope) {
                Object self = scope.get("self");
                Object parent = scope.get("parent");
                Object result = parent.call("copy", new Object[]{});
                HashMap<String,Object> original = self.getNativeObject();
                HashMap<String,Object> copy = new HashMap<String,Object>(original);
                result.setNativeObject(copy);
                scope.set("result", result);
            }
        }
        dictionaryToBe.declare(new CopyMethod());
    }

    private void declareDictionary() {
        class DictionaryMethod extends Method {
            public DictionaryMethod() {
                super("Dictionary");
            }
            protected void execute(Scope scope) {
                scope.set("result", dictionaryToBe.copy());
            }
        }
        world.get("Objects").declare(new DictionaryMethod());
    }
}
