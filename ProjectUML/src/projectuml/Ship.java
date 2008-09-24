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
    protected String imagePath = "/resources/images/";

    /** Creates a new instance of Ship */
    public Ship() {
        destroyed = false;
        // Create destructionAnimation animation
        destructionAnimation = new AnimatedSprite();
        // Should be solved more dynamically
        destructionAnimation.addImage(loadImage(imagePath + "explosion1.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion2.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion3.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion4.png"));
        destructionAnimation.addImage(loadImage(imagePath + "explosion5.png"));
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
     * Update position of the ship onscreen.
     * A ship is always moving in space.
     */
    public void update() {
        if(active){
            Point newPosition = new Point();
            double x = position.getX() + speed;
            double y = position.getY();
            newPosition.setLocation(x, y);
            position = newPosition;
        }
        // If we are destroyed we should make sure that
        // the animation is updated.
        if(destroyed){
            destructionAnimation.update();
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

    protected void destroyShip() {  
        hide();           // Make sure ship isn't drawn
        deactivate();     // Don't do update()
        destructionAnimation.setPosition(position);
        destructionAnimation.show();
        destructionAnimation.activate();
        destroyed = true; // Mark as destroyed
    }

    /**
     * Check if the ship is destroyed
     */
    public Boolean isDestroyed() {
        return destroyed;
    }
    
    public AnimatedSprite getDestructAnimation(){
        return destructionAnimation;
    }
    
    public void setDestructionAnimation(AnimatedSprite animation){
        destructionAnimation = animation;
    }
}
