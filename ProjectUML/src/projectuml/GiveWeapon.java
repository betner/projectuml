package projectuml;

/**
 * GiveWeapon
 * 
 * This provides behaviour for a "touched" sprite.
 * It will give a PlayerShip a weapon.
 *  
 * @see TouchBehaviour
 * @author Steve Eriksson, Jens Thuresson
 */
public class GiveWeapon extends TouchBehaviour {

    private Weapon weapon; // Weapon to give

    /** 
     * Creates a new instance of GiveWeapon .
     *
     * @param weapon
     */
    public GiveWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Give weapon to ship if it's a PlayerShip.
     */
    public void action(Sprite sprite) {
        if (sprite instanceof PlayerShip) {
            Ship ship = (Ship) sprite;
            ship.addWeapon(weapon);
        }
    }
}
