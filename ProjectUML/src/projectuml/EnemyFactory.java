
package projectuml;

/**
 * EnemyFactory
 * 
 * Rudimentary factory that creates enemyships
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyFactory {
    
        
          /**
     * Creates a default enemy
     * 
     * @return EnemyShip
     */
    public static EnemyShip createEnemy(Path path) {
        return EnemyFactory.createEnemyShip1(path);
    }
    
    /**
     * Create enemy of type 1.
     *
     * @param path that ship should follow
     * @return EnemyShip
     */
    public static EnemyShip createEnemyShip1(Path path) {
        return new EnemyShip(path, "enemyship1.png");
    
    }
}
        
    
 
