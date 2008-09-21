
package projectuml;

import java.awt.*;


/**
 * Missile shot
 *
 * @author Steve Eriksson
 */
public class MissileShot extends Shot{
    
    /** Creates a new instance of MissileShot */
    public MissileShot(Point position) {
        image = Toolkit.getDefaultToolkit().getImage("MissileShot.png");
        /* Set objects width and height based on the
         * image that represents it on screen */
        width = image.getWidth(null);  
        height = image.getHeight(null);
        this.position = position;
        velocity = 100;
        damage = 1000;
    }
    
}
