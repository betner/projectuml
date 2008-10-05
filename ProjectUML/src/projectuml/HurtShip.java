
package projectuml;

/**
 * HurtShip
 *
 * This behaviour damages ships.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class HurtShip extends TouchBehaviour {
    
    private int damage;
    
    /**
     * Creates a new instance of Hurt
     */
    public HurtShip(int damage){
        this.damage = damage;
    }
    
    public void action(Sprite sprite){
        if(sprite instanceof Ship){
            Ship ship = (Ship)sprite;
            ship.decreaseHealth(damage);
        }
    }
    

}
