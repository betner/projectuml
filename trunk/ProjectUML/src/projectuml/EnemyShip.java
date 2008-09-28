
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
public class EnemyShip extends Ship{

    private final int SPEED = 5;  // Maximum speed
    private Timestamp time;       // Used to check if given time period has passed
    private Point weaponMountMid; // Weapon placement front center
    private int offset;           // Where enemy should appear in relation to level
    private Point nextPosition;   // Next point to get to
    private Path path;            // Path to follow
    private double angleSin;      
    private double angleCos;
    private double hypotenuse;
    private double diffX;
    private double diffY;
    
    public EnemyShip(Path path, String imageFile){
        this.path = path;
        setPosition(path.next()); // Load the first position
        setImage(loadImage(imageFile));
    }
    
    /**
     * Update ships position. 
     * Ship follows the points provided by path object.
     * First position in path list must be the position in which
     * the ship is created on the screen.
     * Ship's takes the shortest path between to points.
     */
    public void update(){
        int remainingMoves = 0;
        double distance = getPosition().distance(nextPosition);
        
        // If the distance between current position and the next is less than 
        // ship's speed, move to next point and continue to the next saving 
        // number of steps ship has left. This way the ship moves with a more
        // even speed and it nullifies any rounding errors which may cause
        // the ship to never reach next point exactly on the pixel.
       if(distance < SPEED){
           setPosition(nextPosition);
           
           // Fix loss of precision by adding 0.5 to the difference
           remainingMoves = (int)(SPEED - distance + 0.5);
       }
        
        // If ship is at the checkpoint, get next point to move to
        if(getPosition().equals(nextPosition)){
            nextPosition = path.next();
            
            // If the previous position was the last to visit
            // we are finished and should cease to exist.
            if(nextPosition == null){
                deactivate();
                hide();
                return;
            }
            
            // Diff between current and next coordinates
            // diffX -> negative = ship is to the right
            // diffY -> negative = ship is is below
            diffX = nextPosition.getX() - getPositionX();
            diffY = nextPosition.getY() - getPositionY();
            
            hypotenuse = getPosition().distance(nextPosition);
            
            // Calculate angle between ship's position and next point
            angleCos = Math.acos(Math.abs(diffX) / hypotenuse);
            angleSin = Math.asin(Math.abs(diffY) / hypotenuse);
        }
        
        // Continue to move to next point and check ship's position
        // relative to the desired position
        double tempDx = 0;
        double tempDy = 0;
        
        // b = c * cos alfa
        // a = c * sin alfa
        tempDx = SPEED * angleCos;
        tempDy = SPEED * angleSin;
        
        // If we had remaining moves left from last update we should
        // add them to the distance we travel this time.
        if(remainingMoves < 0){
            tempDx += remainingMoves * angleCos;
            tempDy += remainingMoves * angleSin;
        }

        // Conversion from double to int later can give rounding error.
        // add 0.5 to the result.
        tempDx += (tempDx < 0 ? -0.5 : 0.5);
        tempDy += (tempDy < 0 ? -0.5 : 0.5);
         
        
        // Normalize speed, dx and dy. This is where we could get
        // loss off precision due to cast from double to int.
        if(tempDx > SPEED){
            setDx(SPEED * Math.signum(diffX));
        }else{
            setDx((int)tempDx); 
        }
        if(Math.abs(diffY) > SPEED){
            setDy(SPEED * Math.signum(diffY));
        }else{
            setDy((int)tempDy);
        }
          
        super.update(); // Move the ship
        
        /*
         * We could add a timer here and make the ship fire it's weapon
         * at a given time interval
         */ 
            
    }
}
