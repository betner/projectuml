
package projectuml;

import java.awt.Point;
/**
 * Weapon
 * 
 * Weapons aren't currently represented as graphic objects
 * onscreen like a Sprite. Weapons create shots in the level
 * when fired. That means it has to have a reference to the
 * current Level object. 
 * Shots originate from weapon's position.
 *
 * @see Shot
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Weapon {
    
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
