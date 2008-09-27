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

    private final int SPEED = 5;
    private Timestamp time;         // Used to check if given time period has passed
    private Point weaponMountMid;   // Weapon placement front center
    private int offset;             // Where enemy should appear in relation to level
    private Point nextPosition;     // Next point to get to
    private Path path;              // Path to follow
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
     * Ship follows the points provided by path  object.
     * First position in path list must be the position in which
     * the ship is created on the screen.
     */
    public void update(){
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
        double tempDx = 0;
        double tempDy = 0;
        
        // b * cos alfa
        // a * sin alfa
        tempDx = SPEED * angleCos;
        tempDy = SPEED * angleSin;

// Conversion from double to int, add 0.5!
               
        
        // Normalize speed, dx and dy
        if(tempDx > SPEED){
            setDx(SPEED * Math.signum(diffX));
        }else{
            setDx(tempDx); // Fix 0.5!!!
        }
        if(Math.abs(diffY) > SPEED){
            setDy(SPEED * Math.signum(diffY));
        }else{
            setDy(tempDy);
        }
           

        
        super.update(); // Move the ship
    }
       
        
        
        
}

