package projectuml;

import java.io.*;

/**
 * TimeSStamp
 * 
 * Records a marker in time and provides methods
 * for measuring how much time has passed since then.
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Timestamp implements Serializable {

    private long stamp;

    /**
     * Creates the timestamp and marks the current
     * elapsed time.
     */
    public Timestamp() {
        reset();
    }

    /**
     * (Re)sets a marker in time here.
     */
    public void reset() {
        stamp = System.currentTimeMillis();
    }

    /**
     * Checks if the specified amount of time have passed
     * since the last set marker.
     * 
     * @param milliseconds
     * @return True or false
     */
    public Boolean havePassed(long milliseconds) {
        long diff = System.currentTimeMillis() - stamp;
        return diff >= milliseconds;
    }

    /**
     * Get amount of time passed since reset.
     * 
     * @return Time passed in milliseconds
     */
    public long getTimePassed() {
        return System.currentTimeMillis() - stamp;
    }

    /**
     * Serialize it as normal AND restart the timer
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        reset();
    }
}
