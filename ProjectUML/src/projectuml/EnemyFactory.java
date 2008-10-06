package projectuml;

import java.util.*;
import java.awt.Point;

/**
 * EnemyFactory
 *
 * Rudimentary factory that creates enemyships
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyFactory implements Iterable<String> {

    private Hashtable<String, EnemyCreator> creators;

    /**
     * Creates the factory
     */
    public EnemyFactory() {
        creators = new Hashtable<String, EnemyCreator>();
        creators.put("Default enemy (w/ crazy gunner)", new DefaultEnemy());
        creators.put("Default enemy (w/ multishot)", new DefaultEnemyMultiShot());
    }

    /**
     * Creates an enemy of the specified type
     * @param type Textual representation of the enemy type
     * @return EnemyShip, or null if it couldn't be found
     */
    public EnemyShip create(String type) {
        if (creators.containsKey(type)) {
            return creators.get(type).createEnemy();
        } else {
            return null;
        }
    }

    /**
     * Provides an iterator for iterating amongst
     * the creator types
     * @return Iterator of String
     */
    public Iterator<String> iterator() {
        return creators.keySet().iterator();
    }

    /**
     * Inner abstract class that represents a factory
     * for creating enemies of a certain type
     */
    abstract private class EnemyCreator {

        /**
         * Creates enemy of this factory's type
         * @param path Path the enemy follows
         * @return
         */
        abstract public EnemyShip createEnemy();
    }

    /**
     * Factory for creating an enemy of default type
     */
    private class DefaultEnemy extends EnemyCreator {
        public EnemyShip createEnemy() {
            ArrayList<Point> weaponMounts = new ArrayList<Point>();
            weaponMounts.add(new Point(1, 20));
            Gunner gunner = new CrazyGunner(500, 2000);
            EnemyShip enemy = new EnemyShip(1, weaponMounts, gunner, Path.create(), "enemyship1.png");
            enemy.addWeapon(new LaserCannon(false));
            enemy.activate();
            enemy.show();
            return enemy;
        }
    }
    
    /**
     * Factory for creating an enemy of default type, but
     * with a different weapon
     */
    private class DefaultEnemyMultiShot extends EnemyCreator {
        public EnemyShip createEnemy() {
            // We use the other factory's enemy, but
            // exchange the weapon
            EnemyShip ship = new DefaultEnemy().createEnemy();
            ship.addWeapon(new MultiWeapon());
            return ship;
        }
    }
}