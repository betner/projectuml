
package projectuml;

import java.util.*;

/**
 * This is a generic ship.
 * It should be subclassed if used in the game.
 * Ship has health and when it gets below zero it breaks
 * and sends player grasping for air in the ice cold void 
 * of space. Player loses on life.
 * 
 * @author Steve Eriksson
 */
public class Ship extends Sprite{
    
    protected int health;                   // Ship's health
    protected int speed;                    // Current speed, pixels per cycle
    protected Boolean destroyed;            //
    protected ArrayList<Weapon> weaponList; // Ship's arsenal
    
    /** Creates a new instance of Ship */
    public Ship() {
        destroyed = false;
        health = 100;
        speed = 5;
    }
    
    /** 
     * Fire ship's ordinance.
     *
     */
    public void fire(Level l){
        for(Weapon w : weaponList){
            w.fire(l);
        }
    }
    
    /**
     * A ship that gets touched doesn't do anything 
     */
    public void touch(Sprite s){ 
        System.out.println(this + " touched by: " + s);
    }
    
    /**
     * Update position of the ship onscreen.
     * A ship is always moving in space.
     */
    public void update(){
        //Update something
    }
    
    /**
     * Increase ship's health. This can be done by
     * power ups.
     **/
    public void increaseHealth(int units){
        health += units;
    }
    
    /**
     * Decrease ships health. This can be done by
     * shots and other weapons.
     */
    public void decreaseHealth(int units){
        health -= units;
        /* If health get below 0 the ship is
         * flagged as being destroyed */
        if(health <= 0){
            destroyed = true;
        }
    }
    
    /**
     * Check if the ship is destroyed
     */
    public Boolean isDestroyed(){
        return destroyed;
    }
}
