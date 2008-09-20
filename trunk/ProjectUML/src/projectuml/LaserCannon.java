
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class LaserCannon extends Weapon{
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon() {
        damage = 100;
        coolDown = 1000; // 1 second
    }
    
    public void fire(){
        return new LaserShot();
    }
    
}
