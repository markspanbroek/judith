package net.spanbroek.judith.runtime;

import net.spanbroek.judith.runtime.Class;
import net.spanbroek.judith.runtime.Scope;
import net.spanbroek.judith.runtime.Method;
import net.spanbroek.judith.Exception;

/**
 * Represents a judith object.
 *
 * @author Mark Spanbroek
 */
public class Object {


    protected State state = null;

    protected static class State {

        /**
        * The parent object. When a method is called which is not present in the 
        * current object, then the method call is forwarded to the parent. The
        * top of the inheritance chain is marked by setting the parent equal to
        * null.
        */
        public Object parent;

        /**
        * The scope of the object, which contains the inner objects, and has a 
        * reference to the outer objects. Inner objects are those objects which are 
        * declared within the object, whereas outer objects are declared outside 
        * the object declaration. For example:
        * <pre>
        *   object outer := "This is an outer object of the foo object"
        *   object foo |[
        *      object inner := "This is an inner object of the foo object"
        *   ]|
        * </pre>
        */
        public Scope scope;


        public Class clazz = new Class();

        /**
         * A native Java object that is associated with this judith object.
         */
        public java.lang.Object aboriginal = null;


        public boolean replaced = false;

    }


    protected Object(State state) {

        this.state = state;

    }

    /**
     * Constructs a new judith object using the specified parent object and 
     * the scope in which the object was created.
     */
    public Object(Object parent, Scope scope) {

        state = new State();
        state.parent = parent.copy();
        state.scope = new Scope(scope);
        state.scope.declare("parent", new Parent(this));

    }

    /**
     * Constructs a new judith object using the specified scope in which the 
     * object was created. The created object will be at the top of the 
     * inheritance chain.
     */
    public Object(Scope scope) {

        state = new State();
        state.parent = null;
        state.scope = new Scope(scope);

    }

    /**
     * Adds an inner object to this object.
     */
    public void declare(String name, Object object) {

        // Add object to inner scope.
        state.scope.declare(name, object);

    }

    /**
     * Adds a method to this object using the specified name.
     */
    public void declare(String name, Method method) {

        // add the specified method to this object's class
        state.clazz.declare(name, method);

    }

    /**
     * Convenience method for calling judith methods from Java.
     * Executes <code>call(name, parameters, this)</code>.
     */
    public Object call(String name, Object... parameters) {

        // Forward the call.
        return call(name, parameters, this);

    }

    /**
     * Calls the method with the specified name, using the specified parameters
     * and caller object. The caller object is the object that called the 
     * method.
     *
     * @throws Exception when the method could not be found.
     */
    public Object call(String name, Object[] parameters, Object caller) {

        // Forward the call.
        return call(name, parameters, this, caller);

    }

    /**
     * Handles a method call. The self object is the object whose method was 
     * called. This is not necessarily equal to the current object, because
     * a call could have been forwarded from a child object.
     *
     * @throws Exception when the method could not be found.
     * @see #call(String, Object[], Object)
     */
    protected Object call(String name, Object[] parameters,
      Object self, Object caller) {

        // Retrieve method from the class
        Method method = state.clazz.getMethod(name, parameters.length);

        // check whether the method was found
        if (method != null) {

            // create a new child scope of the object scope, containing the
            // 'self' and 'caller' objects
            Scope scope = new Scope(state.scope);
            scope.declare("self", self);
            scope.declare("caller", caller);

            // execute the method
            return method.execute(parameters, scope);

        }
        else {

            // forward the call to the parent object, if it exists.
            if (state.parent != null) {

                return state.parent.call(name, parameters, self, caller);

            }
            else {


                // create reader-friendly description of the method
                String description = name;
                if (parameters.length > 0) {
                    description += "(";
                    for (int i=0; i<parameters.length; i++) {
                        if (i>0) {
                            description += ", ";
                        }
                        description += "object";
                        if (parameters.length > 1) {
                            description += i;
                        }
                    }
                    description += ")";
                }

                // throw exception describing the method that was not found.
                throw new Exception("unknown method: " + description);

            }

        }

    }

    /**
     * Returns the native Java object that is associated with this judith
     * object, or with one of its ancestors.
     *
     * @return the native Java object, or <code>null</code> when no native Java
     *   object was associated with this judith object, or with one of its
     *   ancestors.
     */
    public java.lang.Object getNative() {

        // Return the native object associated with this object or an ancestor.
        if (state.aboriginal != null) {
            return state.aboriginal;
        }
        else {
            if (state.parent != null) {
                return state.parent.getNative();
            }
            else {
                return null;
            }
        }

    }

    /**
     * Associates the specified native Java object with this judith object.
     */
    public void setNative(java.lang.Object aboriginal) {

        // Set the native object.
        state.aboriginal = aboriginal;

    }

    /**
     * Creates a copy of this object. Its inner scope is copied, but the outer
     * scope and class are shared between all copies.
     */
    public Object copy() {

        // copy inner scope, and recurse into the parent, if there is one
        State newState = new State();

        if (state.parent != null) {
            newState.parent = state.parent.copy();
        }
        else {
            newState.parent = null;
        }

        newState.scope = state.scope.copy();
        newState.clazz = state.clazz;
        newState.aboriginal = state.aboriginal;

        return new Object(newState);

    }

    /**
     * Determines whether or not this object is compatible with the specified 
     * object.
     * For each object 'A' holds that it is compatible with object 'B', when
     * 'A' is of the same class as 'B', or if the parent of 'A' is compatible
     * with 'B'.
     */
    public boolean isCompatibleWith(Object object) {

        // Check for equivalence of the class.
        if (state.clazz == object.state.clazz) {
            return true;
        }
        else {
            // Forward call to parent, if possible.
            if (state.parent != null) {
                return state.parent.isCompatibleWith(object);
            }
            else {
                return false;
            }
        }

    }

    public boolean isDescendantOf(Object object) {

        if (state.parent != null) {
            if (state.parent.equals(object)) {
                return true;
            }
            else {
                return state.parent.isDescendantOf(object);
            }
        }
        else {
            return false;
        }

    }

    /**
     * Tests for equality.
     */
    public boolean equals(Object object) {

        // Use java object equivalence.
        return state.equals(object.state);

    }

    /**
     * Registers the specified object as a replacement for this object.
     */
    public void replace(Object object) {

        if (object.isCompatibleWith(this)) {

            // TODO: resolve all replacements before replacing this object

            // create truncated replacement object for registration in class
            Object replacement = object.copy();
            Object ancestor = replacement;
            while (ancestor.state.parent.state.clazz != this.state.clazz) {
                ancestor = ancestor.state.parent;
            }
            ancestor.state.parent = null;

            // register replacement in class
            state.clazz.replace(replacement);

            // replace this object by the replacement object
            this.state = object.state;

        }
        else {
            throw new Exception(
              "an object may only be replaced by a compatible object"
            );
        }

    }

}