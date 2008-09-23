
package projectuml;

import java.awt.*;


/**
 * LaserShot
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class LaserShot extends Shot{
    
    /** Creates a new instance of LaserShot */
    public LaserShot(Point position) {
        this.position = position;
        imageFile = "";
        image = loadImage(imageFile);
        velocity = 100;
        damage = 100;
    }

   
    

    
}
