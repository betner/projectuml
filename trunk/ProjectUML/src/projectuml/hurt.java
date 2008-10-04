
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class hurt extends TouchBehaviour {
    
    private int damage;
    
    /**
     * Creates a new instance of hurt
     */
    public hurt(int damage){
        this.damage = damage;
    }
    
    public void action(Sprite sprite){
    }
    
    public void action(Ship ship){
        ship.decreaseHealth(damage);
    }
}
