package net.spanbroek.judith.runtime;


public class SelflessObject extends Object {

    public SelflessObject(Object parent, Scope scope) {
        super(parent, scope);
    }

    @Override
    protected Scope createScopeForMethodExecution(Object self, Object caller) {
        return new Scope(getCore().getScope());
    }

}
