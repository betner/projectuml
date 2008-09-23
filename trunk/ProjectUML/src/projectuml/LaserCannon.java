
package projectuml;

import java.awt.Point;

/**
 *
 * @author Steve Eriksson
 */
public class LaserCannon extends Weapon{
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon(Point position) {
        this.position = position;
    }
    
    public void fire(Level level){
        // ToDo:
        // add code for cool down timer
        //level.addShot(new LaserShot(position));
    }
    
}
