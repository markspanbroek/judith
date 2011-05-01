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
    protected Object call(String name, Object[] parameters, Object self, Object caller) {
        return receiver.call("call", world.wrap(name), world.wrap(parameters));
    }

}
