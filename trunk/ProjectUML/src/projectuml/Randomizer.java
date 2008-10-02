
package projectuml;

/**
 * Randomizer
 *
 * Static class that provides a method to generate a random number
 *
 * @author Steve Eriksson, Jens Thuresson
 */
final class Randomizer {
    
    static private java.util.Random random = new java.util.Random();

    /**
     * Produce a random number
     *
     * @param floor
     * @param ceil
     * @return integer n between floor and ceiling
     */
    static public int getRandomNumber(int floor, int ceil){
        return random.nextInt((ceil-floor)+1)+floor;
    }
}

