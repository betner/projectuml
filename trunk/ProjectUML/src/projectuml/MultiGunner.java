
package projectuml;

/**
 * Shoots multiple shots at regular intervals
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class MultiGunner extends Gunner {
  
    private final int TIME = 1000;
    
    public MultiGunner(Ship ship) {
        setTimestamp(new Timestamp());
        setShip(ship);
    }
    
    void update(Level level) {
        if (getTimestamp().havePassed(TIME)) {
            getTimestamp().reset();
            
            Shot shot1 = new Shot(1, 1, 1, getShip().getPosition(), "lasershot.png");
            Shot shot2 = new Shot(1, 1, -1, getShip().getPosition(), "lasershot.png");
            level.addEnemyShot(shot1);
            level.addEnemyShot(shot2);
        }
    }
   
}
