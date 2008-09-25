package projectuml;

import java.awt.Point;

/**
 * This is the ship that the player uses.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class PlayerShip extends Ship {
    
    static final Point startingPosition = new Point(20, 200);
    private Player player;

    /** 
     * Creates a new instance of PlayerShip 
     *
     */
    public PlayerShip(){
        this(new Point(0,0)); // Default is upper left corner
    }
    
    public PlayerShip(Point position) {
        setPosition(position);
       // startingPosition = new Point(20, 200);
        setImageFile("playership.png"); //resources/images/playership.png";
        setImage(loadImage(getImageFile()));
        setHeight(getImage().getHeight());
        setWidth(getImage().getWidth());
        // Set default weapon, attach it to the
        // same position as the spaceship
        addWeapon(new LaserCannon(position));
        show();
        activate();
        System.out.println("PlayerShip created:");
        System.out.println("=> Width: " + getWidth());
        System.out.println("=> Height: " + getHeight());
        System.out.println("=> Health: " + getHealth());
        
    }

    /**
     * Overridden update().
     * Calls on super.update() and adds the functionality
     * that if the ship is destroyed and the animation
     * is done the ship should be reset to it's starting position.
     * 
     */
    public void update(){
        // Ship is destroyed and its destruction animation is
        // done so we should "restart" by showing the ship again
        // and placing it iin it's starting position.
        if(isDestroyed() && getDestructAnimation().isDone()){
            super.update();
            // Ship is destroyed and the animation is finished
            // so we should reset the ship
            setPosition(startingPosition); 
            System.out.println("Starting position: " + startingPosition);
            resetShip();
        }else{
            super.update(); // Handle movement
            //System.out.println("PlayerShips: super.update()");
        }
        
        // Ship should only move when user press direction key
        //dy = dx = 0;
    }
    
    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed from the player. 
     */
    public void destroyShip(){
        //System.out.println("PlayerShip: super.destroyShip()");
        super.destroyShip();
        
//        player.removeLife();
        
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
