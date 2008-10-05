
package projectuml;

import java.awt.Point;
import java.util.ArrayList;

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
        ArrayList<Point> weaponMounts = new ArrayList<Point>();
        weaponMounts.add(new Point(1, 20));
        Gunner gunner = new CrazyGunner(500, 2000);
        EnemyShip enemy = new EnemyShip(1, weaponMounts, gunner, path, "enemyship1.png");
        enemy.addWeapon(new LaserCannon(false));
        enemy.activate();
        enemy.show();
        return enemy;
    }
}



