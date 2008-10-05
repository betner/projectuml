
package projectuml;

/**
 *
 * @author Steve Eriksson
 */
public class GiveWeapon  extends TouchBehaviour{
    
    private Weapon weapon;
    /** Creates a new instance of GiveWeapon */
    public GiveWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    
    /**
     * Give weapon to ship if it's a PlayerShip.
     */
    public void action(Sprite sprite){
        if(sprite instanceof PlayerShip){
            Ship ship = (Ship)sprite;
            ship.addWeapon(weapon);
        }
    }
}
