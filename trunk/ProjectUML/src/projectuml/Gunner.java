package projectuml;

import java.io.Serializable;

/**
 * Gunner
 *
 * Provides behaviour for how a ship fires it's weapons.
 *
 * @see EnemyShip
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Gunner implements Serializable {

    private Ship ship;

    /**
     * Creates a new instance of Gunner. 
     */
    public Gunner() {
    }

    abstract void update(Level level);

    /**
     * Get reference to gunner's ship
     * 
     * @return Ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * Set gunner's ship.
     * 
     * @param ship
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
