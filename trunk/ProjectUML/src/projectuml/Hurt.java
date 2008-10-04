
package projectuml;

/**
 * Hurt
 *
 * This behaviour damages ships.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class Hurt extends TouchBehaviour {
    
    private int damage;
    
    /**
     * Creates a new instance of Hurt
     */
    public Hurt(int damage){
        this.damage = damage;
    }
    
    public void action(Sprite sprite){
    }
    
    public void action(Ship ship){
        ship.decreaseHealth(damage);
    }
}
