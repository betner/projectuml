package projectuml;

import java.util.*;
import java.awt.*;

/**
 * EnemyFactory
 *
 * Rudimentary factory that creates enemyships
 *
 * @see EnemyShip
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyFactory extends GeneralFactory<EnemyShip> {

    /**
     * Create the factory and initiate hashtable that holds the 
     * possible enemy types it can make.
     */
    public EnemyFactory() {
        add("Default enemy (w/ crazy gunner)", new DefaultEnemy());
        add("Default enemy (w/ multishot)", new DefaultEnemyMultiShot());
        add("Enemy with missilelauncher", new EnemyMissileLauncher());
        add("Default boss", new DefaultBoss());
    }

    /**
     * Factory for creating an enemy of default type
     * 
     * @return EnemyShip
     */
    private class DefaultEnemy extends GeneralFactoryCreator<EnemyShip> {

        public EnemyShip create() {
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
    private class DefaultEnemyMultiShot extends GeneralFactoryCreator<EnemyShip> {

        public EnemyShip create() {
            // We use the other factory's enemy, but
            // exchange the weapon
            EnemyShip ship = new DefaultEnemy().create();
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
    private class EnemyMissileLauncher extends GeneralFactoryCreator<EnemyShip> {

        public EnemyShip create() {
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
    private class DefaultBoss extends GeneralFactoryCreator<EnemyShip> {

        public EnemyShip create() {
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


    
