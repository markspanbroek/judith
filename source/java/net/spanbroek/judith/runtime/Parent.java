package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Exception;

public class Parent extends Object {

    private Object wrapped;
    private Object self;

    public Parent(Object wrapped, Object self) {
        super((ReplaceableObject)null);
        this.wrapped = wrapped;
        this.self = self;
    }

    @Override
    ReplaceableObject getCurrentCore() {
        return wrapped.getCurrentCore();
    }

    @Override
    void setCore(ReplaceableObject core) {
        wrapped.setCore(core);
    }

    @Override
    protected Object call(MethodCall methodCall) {
        return wrapped.call(createForward(methodCall));
    }
    
    @Override
    public Object copy() {
        throw new Exception(
          "parent objects can not be copied, or derived from"
        );
    }

    protected MethodCall createForward(MethodCall methodCall) {
        final String name = methodCall.getName();
        final Object[] parameters = methodCall.getParameters();
        return new MethodCall(self, name, parameters);
    }
}
