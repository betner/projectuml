
package projectuml;

/**
 * Gunner
 *
 * Provides behaviour for ship to fire it's weapons.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Gunner {
    
    private Level level;
    
    /** Creates a new instance of Gunner */
    public Gunner(Level level) {
        this.level = level;
    }
    abstract void update(Ship ship);
}
