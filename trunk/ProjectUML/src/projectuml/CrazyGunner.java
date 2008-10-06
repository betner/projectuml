package projectuml;

/**
 * CrazyGunner
 * 
 * Provides behaviour for how an enemyship fire's it's weapons.
 * This one calls ship's fire method in a random manner based 
 * on time.
 *
 * @see Ship
 * @see Gunner
 * @author Steve Eriksson, Jens Thuresson
 */
public class CrazyGunner extends Gunner {

    private int maxTime;
    private int minTime;
    private int time;
    private Timestamp timestamp;

    /**
     * Creates new instance of CrazyGunner
     * Ship must be bound to this gunner before it is
     * used by calling setShip(Ship).
     * 
     * @param maxTime maximum time before gunner fires
     * @param minTime minimum time before gunner fires
     */
    public CrazyGunner(int minTime, int maxTime) {
        this.maxTime = maxTime;
        this.minTime = minTime;
        timestamp = new Timestamp();
        time = Randomizer.getRandomNumber(minTime, maxTime);
    }

    /**
     * Overriden update.
     * If time interval has passed the gunner fires and 
     * the timer is reset with new random value.
     * 
     * @param level reference
     */
    public void update(Level level) {
        if (timestamp.havePassed(time)) {
            time = Randomizer.getRandomNumber(minTime, maxTime);
            getShip().fire(level);
            timestamp.reset();
        }
    }
}
