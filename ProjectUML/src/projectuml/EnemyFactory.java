package projectuml;

import java.util.*;
import java.awt.Point;

/**
 * EnemyFactory
 *
 * Rudimentary factory that creates enemyships
 *
 * @see EnemyShip
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyFactory implements Iterable<String> {

    private Hashtable<String, EnemyCreator> creators;

    /**
     * Create the factory and initiate hashtable that holds the 
     * possible enemy types it can make.
     */
    public EnemyFactory() {
        creators = new Hashtable<String, EnemyCreator>();
        creators.put("Default enemy (w/ crazy gunner)", new DefaultEnemy());
        creators.put("Default enemy (w/ multishot)", new DefaultEnemyMultiShot());
        creators.put("Enemy with missilelauncher", new EnemyMissileLauncher());
        creators.put("Default boss", new DefaultBoss());
    }

    /**
     * Creates an enemy of the specified type.
     * 
     * @param type Textual representation of the enemy type
     * @return EnemyShip or null if it couldn't be found
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
     * the creator types.
     * 
     * @return Iterator of String
     */
    public Iterator<String> iterator() {
        return creators.keySet().iterator();
    }

    /**
     * Inner abstract class that represents a factory
     * for creating enemies of a certain type.
     */
    abstract private class EnemyCreator {

        /**
         * Creates enemy of this factory's type.
         * 
         * @param path Path the enemy follows
         */
        abstract public EnemyShip createEnemy();
    }

    /**
     * Factory for creating an enemy of default type
     * 
     * @return EnemyShip
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
     * with a different weapon.
     * 
     * @return EnemyShip
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

    /**
     * Factory for creating an enemy equipped with 
     * a missile launcher.
     * 
     * @return EnemyShip
     */
    private class EnemyMissileLauncher extends EnemyCreator {

        public EnemyShip createEnemy() {
            ArrayList<Point> weaponMounts = new ArrayList<Point>();
            weaponMounts.add(new Point(1, 20));
            Gunner gunner = new CrazyGunner(500, 2000);
            EnemyShip enemy = new EnemyShip(1, weaponMounts, gunner, Path.create(), "enemyship2.png");
            enemy.addWeapon(new MissileLauncher(false));
            enemy.activate();
            enemy.show();
            return enemy;
        }
    }

    /**
     * Factory for creating a default boss.
     * 
     * @return EnemyShip
     */
    private class DefaultBoss extends EnemyCreator {

        public EnemyShip createEnemy() {
            ArrayList<Point> weaponMounts = new ArrayList<Point>();
            weaponMounts.add(new Point(1, 20));
            weaponMounts.add(new Point(1, 40));
            Gunner gunner = new CrazyGunner(500, 2000);
            EnemyShip enemy = new EnemyShip(1, weaponMounts, gunner, Path.create(), "boss1.png");
            enemy.addWeapon(new LaserCannon(false));
            enemy.addWeapon(new MultiWeapon());
            enemy.increaseHealth(10000);
            enemy.activate();
            enemy.show();
            return enemy;
        }
    }
}


    