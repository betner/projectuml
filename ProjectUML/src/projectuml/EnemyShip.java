package projectuml;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * EnemyShip
 *
 * This is a ship that moves by itself. The movement is controlled by
 * a path object and firing is controlled by a gunner object.
 *
 * @see Ship
 * @see Gunner
 * @see Path.java
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyShip extends Ship {

    private final int SPEED = 3;  // Maximum speed
    private Timestamp time;       // Used to check if given time period has passed
    private ArrayList<Point> weaponMounts; // List of positions where weapons are attached
    private int offset;           // Where enemy should appear in the level
    private Point nextPosition;   // Next point to get to
    private Path path;            // Path to follow
    private Navigator navigator;  // Movement behaviour
    private Gunner gunner;        // Firing behaviour

    /**
     * Create EnemyShip
     *
     * @param health
     * @param ArrayList weapon positions
     * @param Gunner firing behaviour
     * @param Path to follow
     * @param imageFile path to image
     */
    public EnemyShip(int health, ArrayList<Point> weaponMounts, Gunner gunner, Path path, String imageFile) {
        // Check if the supplied path is valid
        if (path == null) {
            System.err.println("EnemyShip: path == null");
            return;
        }

        this.path = path;
        nextPosition = path.next();

        // Check if the path contains positions
        if (nextPosition == null) {
            System.err.println("nextPosition == null");
            return;
        }

        // Initialize gunner and add it to this ship
        this.gunner = gunner;
        if (gunner == null) {
            System.err.println("Gunner == null!");
        }
        gunner.setShip(this);

        // Set ship's starting position to be the first in 
        // the supplied path
        setPosition(nextPosition);
        navigator = new SimpleLineFollower(nextPosition);
        navigator.setMaxMovement(SPEED);
        setImage(loadImage(imageFile));

        // Set ship's health
        increaseHealth(health);

        // Set up ship weapon. Weaponmounts must be setup before
        // any weapons can be added so this check is important
        this.weaponMounts = (ArrayList<Point>) weaponMounts.clone();
        if (this.weaponMounts.isEmpty()) {
            System.err.println("WeaponMount array is empty!");
            return;
        }

        getWeaponList().setNumberOfWeapons(weaponMounts.size());
        setWeaponMounts();
    }

    /**
     * Update ships position.
     * Ship follows the points provided by path object.
     * First position in path list is the ship's starting position.
     *
     * @param level reference to current level
     */
    public void update(Level level) {
        if (isActive()) {
            // If ship is at nextPosition, get new destination from path
            if (getPosition().equals(nextPosition)) {
                nextPosition = path.next();

                // If ship is at the end of the path it should disappear
                if (nextPosition == null) {
                    deactivate();
                    hide();
                    return;
                // Otherwise, update navigator calculator with new destination
                } else {
                    navigator.newDestination(nextPosition);
                }
            }

            // Update new position if ship isn't destroyed
            if (!isDestroyed()) {
                setPosition(navigator.getNextPosition(getPosition()));

            // Ship is destroyed. Chances are that it will leave
            // a powerup behind.
            // This should be solved a little more dynamic if time permits.
            } else if (getDestructAnimation().isDone()) {
                // TODO: should powerupfactory be a static class?
                PowerUpFactory powerups = new PowerUpFactory();
                PowerUp powerup = powerups.createRandom();
                powerup.setPosition(getPosition());
                level.addPickable(powerup);
                deactivate();
            }

            gunner.update(level);
            super.update(level);

        }
    }

    /**
     * Set up weaponmounts for the weaponlist.
     */
    protected void setWeaponMounts() {
        for (Point point : weaponMounts) {
            getWeaponList().addWeaponMount(point);
        }
    }

    /**
     * Set path to use.
     *
     * @param path to follow
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Get current path.
     * 
     * @return path or null.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Get enemy's offset.
     *
     * @return offset integer
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Set enemy's offset.
     *
     * @param offset integer
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Set ships gunner object, this changes the way
     * the enemy fires it's weapons.
     * 
     * @param gunner
     */
    public void setGunner(Gunner gunner) {
        this.gunner = gunner;
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        nextPosition = path.next();
        navigator = new SimpleLineFollower(nextPosition);
        navigator.setMaxMovement(SPEED);
    }
}
