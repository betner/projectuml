package projectuml;

import java.awt.Point;

/**
 * This is the ship that the player uses.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class PlayerShip extends Ship {
    
    private Point startingPosition;
    private Player player;

    /** 
     * Creates a new instance of PlayerShip 
     *
     */
    public PlayerShip(){
        this(new Point(0,0)); // Default is upper left corner
    }
    
    public PlayerShip(Point position) {
        this.position = position;
        startingPosition = new Point(20, 200);
        imageFile = "resources/images/playership.png";
        image = loadImage(imageFile);
        height = image.getHeight();
        width = image.getWidth();
        // Set default weapon, attach it to the
        // same position as the spaceship
//        weaponList.add(new LaserCannon(position)); 
        show();
        activate();
        System.out.println("PlayerShip created:");
        System.out.println("=> Width: " + getWidth());
        System.out.println("=> Height: " + getHeight());
        System.out.println("=> Health: " + health);
        
    }

    public void update(){
        // Ship is destroyed and its destruction animation is
        // done so we should "restart" by showing the ship again
        // and placing it iin it's starting position.
        if(destroyed && getDestructAnimation().isDone()){
            setPosition(startingPosition);
            show();    
            activate();
            System.out.println("PlayerShip is destroyed");
        }else{
            super.update(); // Handle movement
            System.out.println("PlayerShips calls: super.update()");
        }
        
    }
    
    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed from the player. 
     * Play animation sequence and after that reset health and 
     * set ship visible and active at start position.
     */
    public void destroyShip(){
        System.out.println("PlayerShip: super.destroyShip()");
        super.destroyShip();
        
//        player.removeLife();
        
    }

}
