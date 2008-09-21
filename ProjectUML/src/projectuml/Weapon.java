
package projectuml;

import java.awt.Point;
/**
 * Generic ordinance.
 * Can implement a coolDown timer that restricts amount 
 * of shots fired in a row.
 * Weapon must get a reference to a Level object so that 
 * it can add a shot to it.
 *
 * @author Steve Eriksson
 */
abstract public class Weapon {
    
    protected double coolDown;
    protected Point position;
    
    /** Creates a new instance of Weapon */
    public Weapon() {
    }
    
    /**
     * Fire weapon
     *
     *@param game level
     */
    abstract public void fire(Level level);
    
}
