
package projectuml;

import java.io.Serializable;

/**
 * Gunner
 *
 * Provides behaviour for how a ship fires it's weapons.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Gunner implements Serializable {
    
    //private Level level;
    private Ship ship;
    private Timestamp timestamp;
    
    /** Creates a new instance of Gunner */
    public Gunner() {
    }
    
    abstract void update(Level level);
    
    
    public Ship getShip(){
        return ship;
    }
    
    public void setShip(Ship ship){
        this.ship = ship;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
}
