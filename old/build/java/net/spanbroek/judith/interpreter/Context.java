package net.spanbroek.judith.interpreter;

import java.util.*;

/**
 * A context consists of a number of named objects, also known as variables. 
 * Contexts can be nested.
 *
 * @author Mark Spanbroek
 */
public class Context implements Cloneable {

    /**
     * The map containing the named objects.
     */
    protected HashMap map = new HashMap();
    
    /**
     * The parent, or enveloping, context.
     */
    private Context parent = this;

    /**
     * Constructs a new context as a child of the specified context.
     */    
    public Context(Context parent) {
        setParent(parent);
    }

    /**
     * Creates a context with no parent context.
     */    
    public Context() {
        // skip
    }

    /**
     * Sets the parent, or enveloping, context.
     */
    public void setParent(Context parent) {
        this.parent = parent;
    }
    
    /**
     * Returns the parent context.
     */
    public Context getParent() {
        return parent;
    }
    
    /**
     * Returns the object that is currently associated with the specified name.
     *
     * @throws RuntimeException when there is no object associated with the
     *   specified name.
     */    
    public Object get(String name) {
        Object result = (Object)map.get(name);
        if (result == null) {
            if (parent != this) {
                result = parent.get(name);
            }
            else {
                // TODO: throw judith exception
                throw new RuntimeException(
                  "unknown object " + name
                );
            }
        }
        return result;
    }

    /**
     * Assigns the specified object to the specified name. This name should have
     * been declared previously.
     *
     * @throws RuntimeException when the specified name hasn't been declared 
     * before.
     */    
    public void set(String name, Object object) {
        if (map.containsKey(name)) {
            map.put(name, object);
        }
        else {
            if (parent != this) {
                parent.set(name, object);
            }
            else {
                // TODO: throw judith exception
                throw new RuntimeException(
                  "unknown object " + name
                );
            }
        }            
    }

    /**
     * Declares a new name and assigns the specified object to that name.
     */    
    public void declare(String name, Object object) {
        map.put(name, object);
    }

    /**
     * Returns a shallow copy of this context, meaning that any declarations 
     * applied to the copy will result in declarations applied to the original.
     */
    public Context shallowCopy() {
        try  {
            return (Context)clone();
        }
        catch (CloneNotSupportedException exception) {
            throw new RuntimeException(exception);
        }        
    }
    
    /**
     * Returns a deep(er) copy of this context, meaning that any declarations 
     * applied to the copy will not result in declarations applied to the 
     * original.
     */
    public Context deepCopy() {
        Context result = shallowCopy();
        result.map = (HashMap)map.clone();
        return result;
    }
    
}
