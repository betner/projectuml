package projectuml;

import java.awt.Point;

/**
 * This is the ship that the player uses.
 * If it loses all it's health it gets destroyed and a destruction
 * animation starts. When a set time has passed since the
 * call to the destroy method, the ship respawns at the start position. 
 * When players life reach zero the ship won't respawn anymore.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class PlayerShip extends Ship {
    
    private final double START_X = 20;
    private final double START_Y = 200;
    private final int SPAWN_TIME = 2000; // Time before ship respawns after destruction 
    private Player player;  // Object representing the player
    private Timestamp time; // Used to check if given time period has passed

    /** 
     * Creates a new instance of PlayerShip 
     *
     */

    
    public PlayerShip(Player player) {
        this.player = player;
        time = new Timestamp();
        setPosition(START_X, START_Y); // Set initial position
        setImageFile("playership.png");
        setImage(loadImage(getImageFile()));
        setHeight(getImage().getHeight());
        setWidth(getImage().getWidth());
        
        // Set default weapon
        // Give it the reference to ship's position object
        // so that it get moved when the ship is
        addWeapon(new LaserCannon(getPosition(), true));
        
        // Make the ship listen to draw() and update() requests
        show();
        activate();
       /* System.out.println("PlayerShip created:");
        System.out.println("=> Width: " + getWidth());
        System.out.println("=> Height: " + getHeight());
        System.out.println("=> Health: " + getHealth());*/
    }

    /**
     * Overridden update().
     * Calls on super.update() and adds the functionality
     * that if the ship is destroyed and the animation
     * is done the ship should be reset to it's starting position
     * unless players life has reached zero.
     * 
     */
    public void update(){
        // Ship is destroyed and its destruction animation is
        // done so we should "restart" by showing the ship again
        // and placing it iin it's starting position.
        if(isDestroyed() && getDestructAnimation().isDone()){
            // Ship is destroyed and the animation is finished
            // so we should reset the ship
            if(time.havePassed(SPAWN_TIME) && player.getLives() > 0){
                setPosition(START_X, START_Y);
                if(player.getLives() > 0){ // Only reset if player has lifes left
                    resetShip();
                }
            }
        }else{
            super.update(); // Handle movement
        }
    }
    
    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed from the player. 
     * Reset timer so that update() can check if there has passed
     * enough time before the ship is reset.
     * Otherwise player gets thrown back in the game at the same 
     * second that the destruction animation is done.
     * 
     */
    public void destroyShip(){
        if(!isDestroyed()){
            time.reset();
            super.destroyShip();
            player.removeLife();
        }
        player.removeLife();
        
    }
    
     /**
     * Methods for steering the ship in eight directions.
     * 
     */
   
    public void goLeft(){
        setDx(-3);
    }
    
    public void goRight(){
        setDx(3);
    }
    
    public void goUp(){
        setDy(-3);
    }
    
    public void goDown(){
        setDy(3);
    }

    public void resetDx(){
        setDx(0);
    }
    
    public void resetDy(){
        setDy(0);
    }
    
  
            
}
