
package projectuml;

import java.awt.Point;

/**
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class LaserCannon extends Weapon{
    
    /** Creates a new instance of LaserCannon */
    public LaserCannon(Point position) {
        System.out.println("LaserCannon created");
        setPosition(position);
    }
    
    public void fire(Level level){
        // ToDo:
        // add code for cool down timer
        //level.addShot(new LaserShot(position));
    }
    
    public void fire(){
        System.out.println("Weapon: fire()");
    }
    
}
