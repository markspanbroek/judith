public class Class {

    protected Class parent;
    protected Scope scope;
    protected Map<String,Method> methods = new HashMap<String,Method>();
    protected int level;

    public Class(Class parent, Scope scope) {

        this.parent = parent;
        this.scope = scope;
        this.level = parent.getLevel() + 1;

    }

    public Class(Scope scope) {

        this.parent = this;
        this.scope = scope;
        this.level = 0;

    }

    public Class getParent() {

        return parent;

    }

    public Scope getScope() {

        return scope;

    }

    public void declare(String name, Method method) {

        String identifier = name + "," + method.getParameterCount();
        methods.put(identifier, method);

    }

    public Method getMethod(String name, int parameterCount) {

        String identifier = name + "," + method.getParameterCount();
        return methods.get(identifier);

    }

    public int getLevel() {

        return level;

    }

}

public class Object {

    protected Class clazz;
    protected Scope scope;

    public Object(Object parent, Scope scope) {

        this.scope = new Scope(scope);
        this.clazz = new Class(parent.clazz, this.scope);

    }

    public Object(Scope scope) {

        this.scope = new Scope(scope);
        this.clazz = new Class(this.scope);

    }

    public void declare(String name, Method method) {

        this.clazz.declare(name, method)

    }

}