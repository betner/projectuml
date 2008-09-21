
package projectuml;

import java.awt.*;


/**
 * Missile shot
 *
 * @author Steve Eriksson
 */
public class MissileShot extends Shot{
    
    private String imageFile;
    
    /** Creates a new instance of MissileShot */
    public MissileShot(Point position) {
        super(position, imageFile);
        velocity = 100;
        damage = 1000;
    }
    
}
