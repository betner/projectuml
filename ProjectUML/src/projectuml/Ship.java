package projectuml;

import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * Ship 
 * 
 * This is a generic ship.
 * It must be subclassed if used in the game.
 * Ship has health and when it gets below zero it is destroyed.
 * Ships have weapons stored in a list. 
 * 
 * @see Sprite
 * @author Steve Eriksson, Jens Thuresson
 */
public abstract class Ship extends Sprite {

    private static final long serialVersionUID = 1L;
    private int maxhealth;      // Ship's max health
    private int health;         // Ship's health
    private int dx;             // Change in x direction, negative is left
    private int dy;             // Change in y direction, negative is down
    private Boolean destroyed;  // Flags the ship as destroyed    
    private WeaponList weaponList;
    transient private AnimatedSprite destructionAnimation; // Animation of ships destructionAnimation

    /**
     * Creates a new instance of Ship 
     * Create animation sprite for the destruction animation and
     * initialize it.
     */
    public Ship() {
        destroyed = false;
        weaponList = new WeaponList();
        // Create destructionAnimation animation
        destructionAnimation = AnimationFactory.createExplosion();//new AnimatedSprite(500, false);
        // Should be solved more dynamically
//        destructionAnimation.addImage(loadImage("explosion1.png"));
//        destructionAnimation.addImage(loadImage("explosion2.png"));
//        destructionAnimation.addImage(loadImage("explosion3.png"));
//        destructionAnimation.addImage(loadImage("explosion4.png"));
//        destructionAnimation.addImage(loadImage("explosion5.png"));
//        destructionAnimation.addImage(loadImage("explosion6.png"));
    }

    /**
     * Setup weaponmounts on ship.
     */
    protected abstract void setWeaponMounts();

    /** 
     * Fire ship's ordinance.
     * If ship is destroyed the ship won't fire.
     * Obviously.
     */
    public void fire(Level level) {
        if (!isDestroyed()) {
            for (Weapon w : weaponList) {
                w.fire(level, this);
            }
        }
    }

    /**
     * Update position of the ship onscreen if it is active.
     * If the ship is destroyed we must let the animation
     * get update() calls.
     */
    public void update(Level level) {
        if (isActive() && !destroyed) {
            updatePosition(dx, dy);
        }
        // If ship is destroyed we should make sure that
        // the animation is updated.
        if(destroyed){
           destructionAnimation.update(level);
        }
    }

    /**
     * Overridden draw method that calls update() on the
     * destruction animation if the ship is flagged as being
     * destroyd.
     * If ship is inactive the super.draw() makes sure
     * it doesn't get drawn.
     * 
     * @param g2D Graphics to draw on
     */
    public void draw(Graphics2D g2D) {
        super.draw(g2D);
        if (destroyed) {
            destructionAnimation.draw(g2D);
        }
    }

    /**
     * Increase ship's health. This can be done by
     * power ups.
     */
    public void increaseHealth(int units) {
        health += units;
        if (health > maxhealth) {
            maxhealth = health;
        }
    }

    /**
     * Decrease ships health. This can be done by
     * shots and other weapons.
     */
    public void decreaseHealth(int units) {
        health -= units;
        if (health <= 0) {
            health = 0;
            destroyShip();
        }
    }

    /**
     * Get ship's current health.
     *
     * @return health integer
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets ship's max health.
     * 
     * @return Integer
     */
    public int getMaxHealth() {
        return maxhealth;
    }

    /**
     * Sets ship's health to a exact amount. Will
     * invoke destroyShip if health is zero or below.
     * 
     * @param health Exact amount of health
     */
    public void setHealth(int health) {
        this.health = health;
        // Leave logic of "what happens when we
        // reach health of below zero" and "is this
        // a new maximum health" to the two other
        // functions, so we don't duplicate
        // code
        increaseHealth(0);
        decreaseHealth(0);
    }

    /**
     * Destroy the ship and start it's destruction animation.
     * The ship is hidden and flagged as destroyed.
     * Here we set the animations position to be the same as 
     * the ships.
     * Ship is still active so it will listen to update() calls.
     */
    protected void destroyShip() {
        hide();           // Make sure ship isn't drawn
        destroyed = true; // Mark as destroyed
        destructionAnimation.reset(); // Make sure the animation is restarted
        destructionAnimation.setPosition(getPosition()); // Set animation position
        destructionAnimation.show();
        destructionAnimation.activate();
    }

    /**
     * Check if the ship is destroyed
     * 
     * @return destroyed
     */
    public Boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Set the destroyed flag to true.
     */
    public void setDestroyed() {
        destroyed = true;
    }

    /**
     * Resets the ship's status.
     * Not destroyed, visible and active.
     */
    public void resetShip() {
        destroyed = false;
        show();
        activate();
    }

    /**
     * Get the animation used for destruction sequence.
     * 
     * @return animated sprite
     */
    public AnimatedSprite getDestructAnimation() {
        return destructionAnimation;
    }

    /**
     * Set animation used for destruction sequence.
     * 
     * @param animated sprite
     */
    public void setDestructionAnimation(AnimatedSprite animation) {
        destructionAnimation = animation;
    }

    /**
     * Return a reference to ship's weaponlist.
     *
     * @return weaponList
     */
    public WeaponList getWeaponList() {
        return weaponList;
    }

    /**
     * Adds a weapon to the ship.
     * Weaponmounts for all possible weapons
     * must be set, otherwise a null pointer exception
     * will be thrown.
     * 
     * @param weapon
     */
    public void addWeapon(Weapon weapon) {
        try {
            getWeaponList().addWeapon(weapon);
        } catch (Exception e) {
            System.err.println("Weapon mount not set for weapon: " + e);
        }
    }

    /**
     * Update DX with new integer values.
     *
     * @param newDx
     */
    public void setDx(int newDx) {
        dx = newDx;
    }

    /**
     * Update DY with new integer values.
     * 
     * @param newDy
     */
    public void setDy(int newDy) {
        dy = newDy;
    }

    /**
     * DX can be set with double values
     * but they will be downcast and can loose
     * some precision.
     * 
     * @param newDx
     */
    public void setDx(double newDx) {
        dx = (int) newDx;
    }

    /**
     * DY can be set with double values
     * but they will be downcast and can loose
     * some precision.
     * 
     * @param newDy
     */
    public void setDy(double newDy) {
        dy = (int) newDy;
    }

    /**
     * Get ship's current dx.
     * 
     * @return dx
     */
    public int getDx() {
        return dx;
    }

    /**
     * Get ship's current dy.
     * 
     * @return dy
     */
    public int getDy() {
        return dy;
    }

    /**
     * Used by the (de)serialization
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        destructionAnimation = AnimationFactory.createExplosion();
    }
}
