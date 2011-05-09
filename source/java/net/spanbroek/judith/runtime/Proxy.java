package net.spanbroek.judith.runtime;

public class Proxy extends Object {

    public Object receiver;
    public World world;

    public Proxy(Object receiver, World world) {
        super(world);
        this.receiver = receiver;
        this.world = world;
    }

    @Override
    protected Object call(MethodCall methodCall) {
        final String name = methodCall.getName();
        final Object[] parameters = methodCall.getParameters();
        return receiver.call("call", world.wrap(name), world.wrap(parameters));
    }

}
