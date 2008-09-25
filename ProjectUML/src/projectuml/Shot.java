
package projectuml;
import java.awt.Point;


/**
 * Shot
 * 
 * Created by a weapon when it is fired.
 * A shot can cause damage to game objects.
 *
 * @see Weapon
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Shot extends Sprite{
  
    //private double velocity; // Shot's speed
    private double dx;
    private double dy;
    private int damage;      // Damage done on impact
    
        
    /** Creates a new instance of Shot */
    public Shot() {
    }
        
    /**
     * Update shot's position. By default we move it horisontally 
     * depending on it's velocity.
     */
    public void update(){
        double x = getPosition().getX() + dx;
        double y = getPosition().getY() + dy;
        setPosition(x, y);
    }
    
    /**
     * Overriding Sprite's touch method.
     * If shot touches a ship it should cause damage
     */
    public void touch(Ship ship){
       ship.decreaseHealth(damage);
    }
    
    public int getDamage(){
        return damage;
    }
    
    public void setDamage(int newDamage){
        damage = newDamage;
    }
                    
    public void setDx(int newDx){
        dx = newDx;
    }
    public void setDy(int newDy) {
        dx = newDy;
    }
        
    public void setDx(double newDx) {
        dx = newDx;
    }
    public void setDy(double newDy) {
        dx = newDy;
    }
}
