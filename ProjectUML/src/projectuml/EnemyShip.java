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

    private final int START_X = 20; // Starting positions
    private final int START_Y = 200;
    private final int BASE_DX = 3;  // Movement speed x-axis. Positive is right
    private final int BASE_DY = 3;  // Movement speed y-axis. Positive is down
    private final int MAX_DX = 5;   // Maximum speed x-axis
    private final int MAX_DY = 5;   // Maximum speed y-axis
    private Timestamp time;         // Used to check if given time period has passed
    private Point weaponMountMid;   // Weapon placement front center
    private int offset;             // Where enemy should appear in relation to level
    private Point nextPosition;     // Next point to get to
    private Path path;              // Path to follow
    private double angleSin;
    private double angleCos;
    private double hypotenuse;
   /* private double currentX;
    private double currentY;
    private double nextX;
    private double nextY;*/
    
    public EnemyShip(){
        //this.path = path;
    }
    
    /**
     * Update ships position. 
     * Ship follows the points provided by path  object.
     */
    public void update(){
        // If ship is at the checkpoint, get next point to move to
        if(getPosition().equals(nextPosition)){
            nextPosition = path.next();
            
            // Diff between current and next coordinates
            // diffX -> negative = ship is to the right
            // diffY -> negative = ship is is below
            double diffX = nextPosition.getX() - getPositionX();
            double diffY = nextPosition.getY() - getPositionY();
            
            hypotenuse = getPosition().distance(nextPosition);
            
            // Calculate angle between ship's position and next point
            angleSin = Math.abs(diffX) / hypotenuse;
            angleCos = Math.abs(diffY) / hypotenuse;

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
        

               
        
        // Normalize speed, dx and dy
        if(Math.abs(tempDx) > MAX_DX){
            setDx(MAX_DX * Math.signum(tempDx));
        }else{
            setDx((int)tempDx);
        }
        if(Math.abs(tempDy) > MAX_DY){
            setDy(MAX_DY * Math.signum(tempDy));
        }else{
            setDy((int)tempDy);
        }
           

        
        super.update(); // Move the ship
    }
       
        
        
        
}

