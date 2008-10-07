package projectuml;

/**
 * HealShip
 * 
 * This behaviour heals ships.
 * 
 * @see TouchBehaviour
 * @author Steve Eriksson, Jens Thuresson
 */
public class HealShip extends TouchBehaviour {

    private int health;

    /**
     * Creates a new instance of HealShip.
     * 
     * @param health
     */
    public HealShip(int health) {
        this.health = health;
    }

    /**
     * If the type of object that is touching is a ship, increase it's health.
     * 
     * @param sprite
     */
    public void action(Sprite sprite) {
        if (sprite instanceof Ship) {
            Ship ship = (Ship) sprite;
            ship.increaseHealth(health);
        }
    }
}