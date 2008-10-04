package projectuml;

import java.io.*;
import java.util.*;
import java.awt.*;

/**
 * This is a generic ship.
 * It must be subclassed if used in the game.
 * Ship has health and when it gets below zero it is destroyed.
 * Ships have weapons stored in a list. 
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public abstract class Ship extends Sprite {
   
    private int health;            // Ship's health
    private int speed;             // Speed relative to gamelevel
    private int dx;             // Change in x direction, negative is left
    private int dy;             // Change in y direction, negative is down
    private Boolean destroyed;     
    private ArrayList<Weapon> weaponList; // Ship's arsenal
    private Level level; // Reference to current level
   
    transient private AnimatedSprite destructionAnimation; // Animation of ships destructionAnimation
    private final String imagePath = "";//"resources/images/";

    /**
     * Creates a new instance of Ship 
     * Create animation sprite for the destruction animation and
     * initialize it.
     */
    public Ship() {
        destroyed = false;
        // Create destructionAnimation animation
        destructionAnimation = new AnimatedSprite(500, false);
        // Should be solved more dynamically
        destructionAnimation.addImage(loadImage(imagePath + "explosion1.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion2.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion3.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion4.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion5.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion6.png"));
        weaponList = new ArrayList<Weapon>();
    }

    /** 
     * Fire ship's ordinance.
     * If ship is destroyed the ship won't fire.
     * Obviously.
     */
    public void fire(Level l) {
        if(!isDestroyed()){
            for (Weapon w : weaponList) {
                w.fire(l);
            }
        }
    }
    
    //DEBUG method
    public void fire(TestDrive td){
        if(!isDestroyed()){
            for (Weapon w : weaponList) {
                w.fire(td);
            }
        }
    }

    /**
     * A ship that gets touched doesn't do anything 
     */
    public void touch(Sprite s) {
    }

    /**
     * Update position of the ship onscreen if it is active.
     * If the ship is destroyed we must let the animation
     * get update() calls.
     */
    public void update(Level level) {
        if(isActive() && !destroyed){
            updatePosition(dx, dy);
        }
        // If ship is destroyed we should make sure that
        // the animation is updated.
        if(destroyed){
           destructionAnimation.update(level);
           // System.out.println("Ship: destructionAnimation.update()");
        }
    }
    
    /**
     * Overridden draw method that calls update() on the
     * destruction animation if the ship is flagged as being
     * destroyd.
     * If ship is inactive the super.draw() makes sure
     * it doesn't get drawn.
     * 
     * @param g2d Graphics to draw on
     */
    public void draw(Graphics2D g2d){
       super.draw(g2d);
       if(destroyed){
           destructionAnimation.draw(g2d);
       }
    }

    /**
     * Increase ship's health. This can be done by
     * power ups.
     **/
    public void increaseHealth(int units) {
        health += units;
    }

    /**
     * Decrease ships health. This can be done by
     * shots and other weapons.
     */
    public void decreaseHealth(int units) {
        health -= units;
        if (health <= 0) {
            destroyShip();
        }
    }
    
   /**
    * Get ship's current health
    *
    * @return health integer
    */
    public int getHealth(){
        return health;
    }

    /**
     * Destroy the ship and start it's destruction animation.
     * The ship is hidden and flagged as destroyed.
     * Here we set the animations position to be the same as 
     * the ships.
     */
    protected void destroyShip() {  
        System.out.println("Ship: destroyShip()");
        hide();           // Make sure ship isn't drawn
        destroyed = true; // Mark as destroyed
        destructionAnimation.reset(); // Make sure the animation is restarted
        destructionAnimation.setPosition(getPosition()); // Set animation position
        destructionAnimation.show();
        destructionAnimation.activate();
       
        System.out.println("Ship: destructionAnimation()");
        System.out.println("Ship: done = true");
    }

    /**
     * Check if the ship is destroyed
     */
    public Boolean isDestroyed() {
        return destroyed;
    }
    
    public void setDestroyed(){
        destroyed = true;
    }
    
    /**
     * Resets the ship's status.
     * Not destroyed, visible and active.
     */
    public void resetShip(){
        destroyed = false;
        show();
        activate();
    }
    
    /**
     * Get the animation used for destruction sequence
     * 
     * @return animated sprite
     */
    public AnimatedSprite getDestructAnimation(){
        return destructionAnimation;
    }
    
    /**
     * Set animation used for destruction sequence
     * 
     * @param animated sprite
     */
    public void setDestructionAnimation(AnimatedSprite animation){
        destructionAnimation = animation;
    }
    
    public void addWeapon(Weapon weapon){
        //System.out.println("Ship: addWeapon()");
        //System.out.println(weapon);
        weaponList.add(weapon);
        
    }

    
    public void setDx(int newDx) {
        dx = newDx;
    }
    public void setDy(int newDy) {
        dy = newDy;
    }
        
    /**
     * DX and DY can be set with double values
     * but they will be downcast and can loose
     * some precision.
     * 
     * @param newDx
     */
    public void setDx(double newDx) {
        dx = (int)newDx;
    }
    public void setDy(double newDy) {
        dy = (int)newDy;
    }
    
    public int getDx(){
        return dx;
    }
    
    public int getDy(){
        return dy;
    }
    
    /**
     * Used by the (de)serialization
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        destructionAnimation = AnimationFactory.createExplosion();
    }
        
}
