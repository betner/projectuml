/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
   /* private double currentX;
    private double currentY;
    private double nextX;
    private double nextY;*/
    
    public EnemyShip(){
        setPosition(path.next());
        //this.path = path;
    }
    
    /**
     * Update ships position. 
     * Ship follows the points provided by path object.
     * First position in path list must be the position in which
     * the ship is created on the screen.
     * Ship's takes the shortest path between to points.
     */
    public void update(){
        // Due to avrundningar the ship could have traveled 1 unit
        // wrong in either x or y direction. So if we are one unit 
        // off in either axis we set ship's position to be the
        // desired position.
       if(getPosition().distance(nextPosition) < 2){
           setPosition(nextPosition);
       }
        
        // If ship is at the checkpoint, get next point to move to
        if(getPosition().equals(nextPosition)){
            nextPosition = path.next();
            
            // Diff between current and next coordinates
            // diffX -> negative = ship is to the right
            // diffY -> negative = ship is is below
            diffX = nextPosition.getX() - getPositionX();
            diffY = nextPosition.getY() - getPositionY();
            
            hypotenuse = getPosition().distance(nextPosition);
            
            // Calculate angle between ship's position and next point
            angleCos = Math.acos(Math.abs(diffX) / hypotenuse);
            angleSin = Math.asin(Math.abs(diffY) / hypotenuse);

            // If the previous position was the last to visit
            // we are finished and should cease to exist.
            if(nextPosition == null){
                deactivate();
                hide();
                return;
            }
        }
        
        // Continue to move to next point and check ship's position
        // relative to the desired position
        double tempDx = 0;
        double tempDy = 0;
        
        // b = c * cos alfa
        // a = c * sin alfa
        tempDx = SPEED * angleCos;
        tempDy = SPEED * angleSin;

        // Conversion from double to int later can give avrundningsfel.
        // add 0.5 to the result.
        tempDx += (tempDx < 0 ? -0.5 : 0.5);
        tempDy += (tempDy < 0 ? -0.5 : 0.5);
        
        // Normalize speed, dx and dy. This is where we could get
        // loss off precision due to cast from double to int.
        if(tempDx > SPEED){
            setDx(SPEED * Math.signum(diffX));
        }else{
            setDx((int)tempDx); // Fix 0.5!!!
        }
        if(Math.abs(diffY) > SPEED){
            setDy(SPEED * Math.signum(diffY));
        }else{
            setDy((int)tempDy);
        }
          
        super.update(); // Move the ship
    }
       
        
        
        
}

