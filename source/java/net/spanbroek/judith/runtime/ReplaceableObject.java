package net.spanbroek.judith.runtime;

import net.spanbroek.judith.Error;

class ReplaceableObject extends NativeObject {

    private ReplaceableClass clazz = new ReplaceableClass();

    private boolean hasBeenReplaced = false;

    boolean hasReplacement() {
        return !hasBeenReplaced && getClazz().hasReplacement();
    }

    ReplaceableObject getReplacement() {
        if (!hasReplacement()) {
            throw new Error("Replacement not available.");
        }
        return createReplacement(getClazz().getReplacement().getCurrentCore());
    }

    /**
     * Call this method to indicate that this object core has been replaced.
     */
    public void markAsReplaced() {
        hasBeenReplaced = true;
    }

    /**
     * Recursively copies the specified core and its ancestors. When the
     * specified core is of the same class as this core, then this core is
     * returned.
     */
    private ReplaceableObject createReplacement(ReplaceableObject core) {
        if (core.getClazz() == getClazz()) {
            return (ReplaceableObject) this;
        }
        ReplaceableObject result = (ReplaceableObject)core.clone();
        if (core.hasParent()) {
            ReplaceableObject parent = core.getParent().getCurrentCore();
            result.setParent(new Object(createReplacement(parent)));
        }
        return result;
    }

    @Override
    ReplaceableObject copy() {
        return (ReplaceableObject)super.copy();
    }

    @Override
    ReplaceableClass getClazz() {
        return clazz;
    }
}
