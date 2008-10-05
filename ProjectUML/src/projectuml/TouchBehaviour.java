
package projectuml;

import java.io.Serializable;

/**
 * TouchBehaviour
 *
 * Gives sprites behaviour when they are touched.
 *
 * @author Steve Eriksson
 */
abstract public class TouchBehaviour implements Serializable{
    
    /** Creates a new instance of TouchBehaviour */
    public TouchBehaviour() {
    }
    
    abstract void action(Sprite sprite);
}
