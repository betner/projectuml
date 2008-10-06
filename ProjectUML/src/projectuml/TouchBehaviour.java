package projectuml;

import java.io.Serializable;

/**
 * TouchBehaviour
 *
 * Gives sprites behaviour that is triggered by call to
 * it's touch() method.
 * 
 * @see Sprite
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class TouchBehaviour implements Serializable {

    /**
     * Creates a new instance of TouchBehaviour 
     */
    public TouchBehaviour() {
    }

    /**
     * Action to perform.
     * 
     * @param sprite
     */
    abstract void action(Sprite sprite);
}
