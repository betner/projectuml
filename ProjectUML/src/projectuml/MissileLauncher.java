
package projectuml;

import java.awt.*;

/**
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class MissileLauncher extends Weapon{
    
    /** Creates a new instance of MissileLauncher */
    public MissileLauncher() {
        coolDown = 1000;
    }
    
    public void fire(Level level){
        level.addShot(new MissileShot(position));
    }
}
