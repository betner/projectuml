
package projectuml;

import java.awt.*;

/**
 * MissileLauncher
 * 
 * Employs a cool down timer so that the number of rounds
 * per minute is restricted. 
 * When fired it creates missile shots in the level.
 * 
 * @see MissileShot
 * @author Steve Eriksson, Jens Thuresson
 */
public class MissileLauncher extends Weapon{
    
    protected int coolDown; // Cool down for weapon
    
    /**
     * Create missile launcher at given point and
     * set cool down time to restrict rounds per minute
     * @param position
     */
    public MissileLauncher(Point position){
        // Jens: added 
        //this.position = position;
        this.setPosition(position);
        
        coolDown = 1000;
    }
    
    public void fire(Level level){
        // TODO
        // Add timer code
        
        // Jens: added
        //level.addShot(new MissileShot(position));
        level.addShot(new MissileShot(this.getPosition()));
    }
}
