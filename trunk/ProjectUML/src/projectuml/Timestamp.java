package projectuml;

/**
 * Records a marker in time and provides methods
 * for measuring how much time has passed since then
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Timestamp {

    private long stamp;
    
    /**
     * Creates the timestamp and marks the current
     * elapsed time
     */
    public Timestamp() {
        reset();
    }
    
    /**
     * (Re)sets a marker in time here, to 
     */
    public void reset() {
        stamp = System.currentTimeMillis();
    }
    
    /**
     * Checks if the specified amount of time have passed
     * since the last set marker
     * @param milliseconds
     * @return True or false
     */
    public Boolean havePassed(long milliseconds) {
        long diff = System.currentTimeMillis() - stamp;
        return diff >= milliseconds;
    }
    
}
