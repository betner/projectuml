
package projectuml;


/**
 * HurtShip
 *
 * This behaviour damages ships.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class HurtShip extends TouchBehaviour{
    
    private int damage;
    
    /**
     * Creates a new instance of HurtShip.
     * 
     * @param damage 
     */
    public HurtShip(int damage){
        this.damage = damage;
    }
    
    /**
     * If the type of object that is touching is a ship, decrease it's health.
     * 
     * @param sprite
     */
    public void action(Sprite sprite){
        if(sprite instanceof Ship){
            Ship ship = (Ship)sprite;
            ship.decreaseHealth(damage);
        }
    }
    

}
