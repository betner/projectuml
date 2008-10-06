
package projectuml;

import java.awt.*;
import java.util.*;

/**
 * EnemyShip
 *
 * This is a ship that moves by itself. The movement is controlled by
 * a path object and firing is controlled by a gunner object.
 *
 * @see Path.java
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyShip extends Ship {
    
    private final int SPEED = 3;  // Maximum speed
    private Timestamp time;       // Used to check if given time period has passed
    private ArrayList<Point> weaponMounts; // List of positions where weapons are attached
    private int offset;           // Where enemy should appear in relation to level
    private Point nextPosition;   // Next point to get to
    private Path path;            // Path to follow
    private Navigator navigator;
    private Gunner gunner;
    

    /**
     * Create EnemyShip
     *
     * @param health
     * @param ArrayList of Points
     * @param Gunner object, firing behaviour
     * @param Path to follow
     * @param imageFile path to image
     */
    public EnemyShip(int health, ArrayList<Point> weaponMounts, Gunner gunner, Path path, String imageFile){
        // Check if the supplied path is valid
        if(path == null){
            System.err.println("EnemyShip: path == null");
            return;
        }
        
        this.path = path;
        nextPosition = path.next();
        
        // Check if the path contains positions
        if(nextPosition == null){
            System.err.println("nextPosition == null");
            return;
        }
        
        // Initialize gunner and add it to this ship
        this.gunner = gunner;
        if (gunner == null){
            System.err.println("Gunner == null!");
        }
        gunner.setShip(this);
        
        setPosition(nextPosition); // Load the first position
        navigator = new SimpleLineFollower(nextPosition);
        navigator.setMaxMovement(SPEED);
        setImage(loadImage(imageFile));
        
        // Set ship's health
        increaseHealth(health);
        
        // Set up ship weapon
        this.weaponMounts = (ArrayList<Point>)weaponMounts.clone();
        if (this.weaponMounts.isEmpty()){
            System.err.println("WeaponMount array is empty!");
            return;
        }
        getWeaponList().setNumberOfWeapons(weaponMounts.size());
        setWeaponMounts();
    }
    
    /**
     * Update ships position.
     * Ship follows the points provided by path object.
     * First position in path list must be the position in which
     * the ship is created on the screen.
     * Ship's takes the shortest path between to points.
     * Due to rounding errors when calculating dx,dy from angle
     * between current and next point we have to recalculate dx, dy
     * every update so that the ship will come close enough to
     * desired position.
     */
    public void update(Level level){
        if(isActive()){
            // If ship is at nextPosition, get new destination from path
            if(getPosition().equals(nextPosition)){
                nextPosition = path.next();
                
                // If ship is at the end of the path it should disappear
                if (nextPosition == null) {
                    deactivate();
                    hide();
                    return;
                    
                    // Otherwise, update navigator calculator with new destination
                }else{
                    navigator.newDestination(nextPosition);
                }
            }
            
            // Update new position if ship isn't destroyed
            if(!isDestroyed()){
                setPosition(navigator.getNextPosition());
                
                // Ship is destroyed. Chances are that it will leave
                // a powerup behind.
                // This should be solved a little more dynamic if time permits.
            }else if(getDestructAnimation().isDone()){
                if(Randomizer.getRandomNumber(0, 0) == 0){
                    if(Randomizer.getRandomNumber(0,1) == 0){
                        level.addPickable(PowerUpFactory.createMissileLauncherPowerUp(getPosition()));
                    }else{
                        level.addPickable(PowerUpFactory.createHealthPowerUp(getPosition(), 100));
                    }
                }
                deactivate();
            }
            
            gunner.update(level);
            super.update(level);
            
        }
    }
    /**
     * Set up weaponmounts for the weaponlist.
     */
    protected void setWeaponMounts(){
        for(Point point : weaponMounts){
            getWeaponList().addWeaponMount(point);
        }
    }
    
    /**
     * Set path to use
     *
     * @param path Path object
     */
    public void setPath(Path path){
        this.path = path;
    }
    
    /**
     * Get enemy's offset
     *
     * @return offset integer
     */
    public int getOffset(){
        return offset;
    }
    
    /**
     * Set enemy's offset
     *
     * @param offset integer
     */
    public void setOffset(int offset){
        this.offset = offset;
    }
    
    /**
     * Set ships gunner object, this changes the way
     * the enemy fires it's weapons.
     */
    public void setGunner(Gunner gunner){
        this.gunner = gunner;
    }
    
}
