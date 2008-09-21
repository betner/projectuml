
package projectuml;

import java.awt.*;


/**
 *
 * @author Steve Eriksson
 */
public class LaserShot extends Shot{
    
    /** Creates a new instance of LaserShot */
    public LaserShot() {
        
        image = Toolkit.getDefaultToolkit().getImage("laserShot.png");
        velocity = 100;
        damage = 100;
    }
    
}
