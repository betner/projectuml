
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class LaserCannon extends Weapon{
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon() {
        coolDown = 1000; // 1 second
    }
    
    public void fire(Level level){
        // ToDo:
        // add code for cool down timer
        return new LaserShot(level);
    }
    
}
