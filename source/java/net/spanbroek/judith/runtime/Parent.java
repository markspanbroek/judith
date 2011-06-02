package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Exception;

/**
 * Wraps an object, and forwards all method calls to the parent object.
 *
 * @author Mark Spanbroek 
 */
class Parent extends Object {

    private Object wrapped;
    private Object self;

    /**
     * Creates a parent object wrapper for the specified object.
     */
    public Parent(Object wrapped, Object self) {
        super((ReplaceableObject)null);
        this.wrapped = wrapped;
        this.self = self;
    }

    /**
     * Returns the current core of the wrapped object. 
     */
    @Override
    ReplaceableObject getCurrentCore() {
        return wrapped.getCurrentCore();
    }

    /**
     * Sets the core of the wrapped object.
     */
    @Override
    void setCore(ReplaceableObject core) {
        wrapped.setCore(core);
    }

    /**
     * Calls the specified method of the wrapped object using the caller
     * of the method as the 'self' value, when the caller is a descendant of the
     * wrapped object. If the caller is not a descendant of the wrapped object,
     * an Exception is thrown.
     */
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
        Object caller = methodCall.getCaller();
        return new MethodCall(self, name, parameters, caller);
    }
}
