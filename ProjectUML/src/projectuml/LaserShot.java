
package projectuml;

import java.awt.*;


/**
 * LaserShot
 *
 * @author Steve Eriksson
 */
public class LaserShot extends Shot{
    
    /** Creates a new instance of LaserShot */
    public LaserShot(Point position) {
        image = Toolkit.getDefaultToolkit().getImage("LaserShot.png");
        /* Set objects width and height based on the
         * image that represents it on screen */
        width = image.getWidth(null);  
        height = image.getHeight(null);
        this.position = position;
        velocity = 100;
        damage = 100;
    }

    public void touch(Sprite s) {
    }

    public void update() {
    }
    

    
}
