
package projectuml;

/**
 * Heal
 *
 * This behaviour heals ships.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class Heal extends TouchBehaviour{
    
    private int health;
    
    /**
     * Creates a new instance of hurt
     */
    public Heal(int health){
        this.health = health;
    }
    
    public void action(Sprite sprite){
        // Not sure if this gets called due to Sprite's implementation of touch'
        System.out.println("heal: action(sprite)"); 
        Ship ship = (Ship)sprite;
        ship.increaseHealth(health);
    }
    
    public void action(Ship ship){
        ship.increaseHealth(health);
    }
}