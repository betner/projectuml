
package projectuml;

import java.awt.Point;

/**
 * EnemyShip
 * 
 * This is a ship that moves by itself. The movement is controlled by
 * a path object.
 * 
 * @see Path.java
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class EnemyShip extends Ship {

    private final int SPEED = 3;  // Maximum speed
    private Timestamp time;       // Used to check if given time period has passed
    private Point weaponMountMid; // Weapon placement front center
    private int offset;           // Where enemy should appear in relation to level
    private Point nextPosition;   // Next point to get to
    private Path path;            // Path to follow
    private Navigator navigator;
    private Gunner gunner;
    
    
    public EnemyShip(Path path, String imageFile){
        
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
        
        // Create a default gunner
        gunner = new CrazyGunner(this);
        
        setPosition(nextPosition); // Load the first position
        navigator = new SimpleLineFollower(nextPosition);
        navigator.setMaxMovement(SPEED);
        setImage(loadImage(imageFile));
        
        // Set ship's health
        increaseHealth(1);
        
        // Set up ship weapon
        getWeaponList().setNumberOfWeapons(1);
       // weaponMountMid = clonePosition(getPosition());
        weaponMountMid = (Point)getPosition().clone();
        setWeaponMounts();
        
        getWeaponList().addWeapon(new LaserCannon(false));
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
        
      // If ship is at nextPosition, get new destination from path
      if(getPosition().equals(nextPosition)){
          nextPosition = path.next();
//          System.out.println(" getPosition().equals(nextPosition = true");
//          System.out.println("nextPosition = " + nextPosition);
          
          // If ship is at the end of the path it should disappear
          if (nextPosition == null) {
//              System.out.println("Enemy: nextPosition == null");
              deactivate();
              hide();
              return;
              
          // Otherwise, update navigator calculator with new destination
          }else{
//              System.out.println("update(): route.newDestination " + nextPosition);
              navigator.newDestination(nextPosition);
          }
      }
        
      // Update new position if ship isn't destroyed
      if(!isDestroyed()){
          setPosition(navigator.getNextPosition());
      }
      
      super.update(level);
        
        /*
         * We could add a timer here and make the ship fire it's weapon
         * at a given time interval or fire when it is a certain position
         * relative to player.
         * 
         */      
    }
    
    protected void setWeaponMounts(){
        Point mid = new Point(1, (getHeight() / 2));
        getWeaponList().addWeaponMount(mid);
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

}
