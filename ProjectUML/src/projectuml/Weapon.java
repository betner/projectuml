
package projectuml;

import java.awt.Point;
/**
 * Generic ordinance.
 * Can implement a coolDown timer that restricts 
 * amount of shots fired in a row.
 *
 * @author Steve Eriksson
 */
abstract public class Weapon {
    
    protected int damage;
    protected double coolDown;
    protected Point position;
    
    /** Creates a new instance of Weapon */
    public Weapon() {
    }
    
    public void fire(){
        // Create new shot at weapons position
    }
    
}
