package mjs.model;

import mjs.utils.Loggable;

/**
 * A Loggable class that uses the Model logging category.
 * This class is declared abstract so that it cannot be
 * directly instantiated. 
 *
 * @author mishoema
 */
public abstract class ModelLoggable extends Loggable {

    /**
     * Constructor.
     */
    public ModelLoggable() {
        super("Model");
    }
}
