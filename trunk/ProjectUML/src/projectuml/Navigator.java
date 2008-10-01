
package projectuml;

import java.awt.Point;
import java.io.Serializable;

/**
 * Navigator
 * 
 * Given a destination a route is calculated and next point on the
 * route can be ombtained by calling newPosition.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Navigator implements Serializable {

    /**
     * Set new goal
     * @param p
     */
    abstract void newDestination(Point p);
    abstract Point getNextPosition();
    abstract void setMaxMovement(int m);
}
