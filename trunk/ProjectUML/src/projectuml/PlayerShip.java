package projectuml;

import java.awt.Point;

/**
 * This is the ship that the player uses.
 * If it loses all it's health it gets destroyed and a destruction
 * animation starts. When a set time has passed since the
 * call to the destroy method, the ship respawns at the start position. 
 * When players life reach zero the ship won't respawn anymore.
 * 
 * Weapons are mounted on one of the three weaponmounts.
 * 
 * 
 * @author Steve Eriksson, Jens Thuresson
 * @see Ship.java
 * @see Weapon.java
 */
public class PlayerShip extends Ship {
    
    private final int START_X = 20; // Starting position
    private final int START_Y = 200;
    private final int SPAWN_TIME = 2000; // Time before ship respawns after destruction 
    private final int START_HEALTH = 1000;
    private final int DX = 3; // Movement speed x-axis. Positive is right
    private final int DY = 3; // Movement speed y-axis. Positive is down
    private Player player;  // Object representing the player
    private Timestamp timestamp; // Used to check if given timestamp period has passed

    /** 
     * Creates a new instance of PlayerShip 
     *
     */
    public PlayerShip(Player player) {
        this.player = player;
        timestamp = new Timestamp();
        
        // Set initial position
        setPosition(START_X, START_Y); 

        loadImageFrom("playership.png");

        // Set initial health
        increaseHealth(START_HEALTH);
        
        // Set up ship's weapons
        getWeaponList().setNumberOfWeapons(3);
        setWeaponMounts();
        getWeaponList().addWeapon(new LaserCannon(true));
        getWeaponList().addWeapon(new MissileLauncher(true));
        // Make the ship listen to draw() and update() requests
        show();
        activate();
    }

    /**
     * Overridden update().
     * Calls on super.update() and adds the functionality
     * that if the ship is destroyed and the animation
     * is done the ship should be reset to it's starting position
     * unless players life has reached zero.
     * 
     */
    public void update(Level level){
        // Ship is destroyed and its destruction animation is
        // done so we should "restart" by showing the ship again
        // and placing it in it's starting position providing that
        // player has lives left.
        if(isDestroyed() && getDestructAnimation().isDone()){
            if(timestamp.havePassed(SPAWN_TIME) && player.getLives() > 0){
                // Move ship to starting location
                setPosition(START_X, START_Y);
                resetShip();
            }
        }else{
            // Handle ship movement
            super.update(level); 
        }
       
    }
    
    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed from the player. 
     * Reset timer so that update() can check if there has passed
     * enough timestamp before the ship is reset.
     * Otherwise player gets thrown back in the game at the same 
     * second that the destruction animation is done.
     */
    public void destroyShip(){
        if(!isDestroyed()){
            timestamp.reset();
            super.destroyShip();
            player.removeLife();
        }
    }
    
    /*
     * Create Point's for all the weapon mounts. These points are relative
     * to upper left corner and not the ship's current position.
     */ 
    protected void setWeaponMounts(){
        // Set correct position for the mounts relative to x,y = 0,0
        Point mid = new Point(getWidth() - 1, (getHeight() / 2));
        Point right = new Point(getWidth() - 1, getHeight());
        Point left = new Point(getWidth() - 1, 0);

        System.out.println("PlayerShip: addWeaponMount");
        
        // Add weapon mounts to ship's weapon list
        getWeaponList().addWeaponMount(mid);
        getWeaponList().addWeaponMount(left);
        getWeaponList().addWeaponMount(right);
    }
    
   
    /**
     * Methods for steering the ship in eight directions.
     * Default values for dx, dy are positive, which means
     * dx = right and dy = down
     */
    public void goLeft(){
        setDx(DX * -1); // Invert movement
    }
    
    public void goRight(){
        setDx(DX);
    }
    
    public void goUp(){
        setDy(DY * -1); // Invert movement
    }
    
    public void goDown(){
        setDy(DY);
    }

    public void resetDx(){
        setDx(0);
    }
    
    public void resetDy(){
        setDy(0);
    }
    
  
            
}
