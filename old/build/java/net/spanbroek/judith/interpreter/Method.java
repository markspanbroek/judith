package net.spanbroek.judith.interpreter;

/**
 * Represents a judith method.
 * @author Mark Spanbroek
 */
public interface Method {

    /**
     * Returns the result of calling this judith method with the specified 
     * parameters, caller object, self object, instance object and context.
     */
    Object call(Object[] parameters, Object caller, Object self
      , Object instance, Context context);

}