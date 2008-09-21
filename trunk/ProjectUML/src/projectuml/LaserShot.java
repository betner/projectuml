
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
        /* Set objects width and height based on the
         * image that represents it on screen */
        width = image.getWidth(null);  
        height = image.getHeight(null);
        velocity = 100;
        damage = 100;
    }
    
}
