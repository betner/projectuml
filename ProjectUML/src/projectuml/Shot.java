
package projectuml;
import java.awt.Point;

/**
 * Generic shot. Created by a weapon.
 *
 * @author Steve Eriksson
 */
abstract public class Shot extends Sprite{
    
    protected double velocity; // Shot's speed
    protected int damage;      // Damage done on impact
    
    /** Creates a new instance of Shot */
    public Shot() {
    }
    
    /**
     * Update shot's position. By default we move it horisontally 
     * depending on it's velocity.
     */
    public void update(){
       Point newPosition = new Point();
       double x = position.getX() + velocity;
       double y = position.getY();
       newPosition.setLocation(x,y);
       position = newPosition;
    }
    
    /**
     * Overriding Sprite's touch method.
     * If shot touches a ship it should cause damage
     */
    public void touch(Ship ship){
       ship.decreaseHealth(damage);
    }
}
