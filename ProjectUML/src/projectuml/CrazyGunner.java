
package projectuml;

/**
 * CrazyGunner
 * 
 * Provides behaviour for how an enemyship fire's it's weapons.
 * This one calls ship's fire method in a random manner based 
 * on time.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class CrazyGunner extends Gunner{
    
    private int maxTime;
    private int time;
    

    /**
     * Creates new instance of CrazyGunner
     *
     * @param ship reference to the ship that contains CrazyGunner
     * @param maxTime maximum time before gunner fires
     */
    public CrazyGunner(Ship ship, int maxTime){
        setShip(ship);
        this.maxTime = maxTime;
        setTimestamp(new Timestamp());
        time = Randomizer.getRandomNumber(500, maxTime); 
    }
    
    /**
     * Creates a new instance of CrazyGunner
     * with default value for maxTime.
     */
    public CrazyGunner(Ship ship) {
        this(ship, 2000);
    }
    
    /**
     * Overriden update
     * If time interval has passed the gunner fires and 
     * the timer is reset with new random value
     **/
    public void update(Level level){
        if(getTimestamp().havePassed(time)){
            // time is between 0 and 2 seconds
            time = Randomizer.getRandomNumber(500, maxTime); 
            //getShip().fire(getLevel());
            getShip().fire(level);
            getTimestamp().reset();
        }
    }
}
