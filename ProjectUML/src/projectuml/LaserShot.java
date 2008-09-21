
package projectuml;

import java.awt.*;


/**
 * LaserShot
 *
 * @author Steve Eriksson
 */
public class LaserShot extends Shot{
    
    /** Creates a new instance of LaserShot */
    public LaserShot(Point position, String imageFile) {
        super(position, imageFile);
        velocity = 100;
        damage = 100;
    }

   
    

    
}
