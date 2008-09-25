package projectuml;

import java.util.*;
import java.awt.*;

/**
 * This is a generic ship.
 * It should be subclassed if used in the game.
 * Ship has health and when it gets below zero it breaks
 * and sends player grasping for air in the ice cold void 
 * of space. Player loses one life.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class Ship extends Sprite {

    protected int health;            // Ship's health
    protected int speed;             // Speed relative to gamelevel
    protected double dx;             // Change in x direction, negative is left
    protected double dy;             // Change in y direction, negative is down
    protected Boolean destroyed;     
    protected ArrayList<Weapon> weaponList; // Ship's arsenal
    protected AnimatedSprite destructionAnimation;     // Animation of ships destructionAnimation
    protected String imagePath = "";//"resources/images/";

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
    }

    /** 
     * Fire ship's ordinance.
     *
     */
    public void fire(Level l) {
        for (Weapon w : weaponList) {
            w.fire(l);
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
    public void update() {
        System.out.println("Ship: update()");
        if(active){
            Point newPosition = new Point();
            double x = position.getX() + dx;
            double y = position.getY() + dy;
            newPosition.setLocation(x, y);
            position = newPosition;
        }
        // If we are destroyed we should make sure that
        // the animation is updated.
        if(destroyed){
            destructionAnimation.update();
            System.out.println("Ship: destructionAnimation.update()");
        }
    }
    
    /**
     * Overridden draw method that calls update() on the
     * destruction animation if the ship is flagged as being
     * destroyd.
     * @param g2d
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
     * Destroy the ship and start it's destruction animation.
     * The ship is hidden and flagged as destroyed.
     */
    protected void destroyShip() {  
        System.out.println("Ship: destroyShip()");
        hide();           // Make sure ship isn't drawn
        destroyed = true; // Mark as destroyed
        destructionAnimation.reset(); // Make sure the animation is restarted
        destructionAnimation.setPosition(position);
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
    
   
}
