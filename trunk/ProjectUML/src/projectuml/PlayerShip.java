package projectuml;

import java.awt.Point;

/**
 * This is the ship that the player uses.
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class PlayerShip extends Ship {

    private Player player;

    /** 
     * Creates a new instance of PlayerShip 
     *
     */
    
    public PlayerShip(){
        this(new Point(0,0)); // Default position is upper left corner
    }
    
    public PlayerShip(Point position) {
        this.position = position;
        imageFile = "/images/playership.png";
        image = loadImage(imageFile);
        height = image.getHeight();
        width = image.getWidth();
    }

    /**
     * Overridden from Ship. 
     * If ship is destroyed, one life is removed
     * from the player
     */
    @Override
    public void destroyShip(){
        super.destroyShip();
        player.removeLife();
    }

}
