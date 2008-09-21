package projectuml;

import java.util.*;

/**
 * This is the ship that the player uses.
 * 
 * @author Steve Eriksson
 */
public class PlayerShip extends Ship {

    private Player player;

    /** Creates a new instance of PlayerShip */
    public PlayerShip() {
    }

    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed
     * from the player
     */
    public void descreaseHealth(int units) {
        health -= units;
        
        // If health gets below 0 the ship is flagged
        // as being destroyed and players life is
        // decreased by 1 
        player.removeLife();
    }
}
