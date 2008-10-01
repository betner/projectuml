
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

    private final int SPEED = 3;  // Maximum speed
    private Timestamp time;       // Used to check if given time period has passed
    private Point weaponMountMid; // Weapon placement front center
    private int offset;           // Where enemy should appear in relation to level
    private Point nextPosition;   // Next point to get to
    private int newX;
    private int newY;
    private Path path;            // Path to follow
    private Navigator route;
//    private double angle;
//    private double hypotenuse;
//    private double diffX;
//    private double diffY;
    
    
    // Test method
    public EnemyShip(){
        path.addPoint(new Point(400, 200));
        setPosition(path.next());
        setImage(loadImage("enemyship1.png"));
    }
    
    // TODO: add code to bind ship to a level
    // so that it can fire weapons
    public EnemyShip(Path path, String imageFile){
        this.path = path;
        nextPosition = path.next();
        if(nextPosition == null){
            System.out.println("nextPosition == null");
            return;
        }
        setPosition(nextPosition); // Load the first position
        route = new SimpleLineFollower(nextPosition);
        route.setMaxMovement(SPEED);
        setImage(loadImage(imageFile));
        
        // Set ship's health
        increaseHealth(1);
        
        // Set up ship weapon
        // Should be fixed so that weapon gets better placement.
        weaponMountMid = clonePosition(getPosition());
        addWeapon(new LaserCannon(weaponMountMid, false));
        
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
    public void update(){
        
      // If ship is at nextPosition, get new destination from path
      if(getPosition().equals(nextPosition)){
          nextPosition = path.next();
          System.out.println(" getPosition().equals(nextPosition = true");
          System.out.println("nextPosition = " + nextPosition);
          
          // If ship is at the end of the path it should disappear
          if (nextPosition == null) {
              System.out.println("Enemy: nextPosition == null");
              deactivate();
              hide();
              return;
              
          // Otherwise, update route calculator with new destination
          }else{
              System.out.println("update(): route.newDestination " + nextPosition);
              route.newDestination(nextPosition);
          }
      }
        
      // Update new position
      setPosition(route.getNextPosition());
      
      
      
//        double distance = getPosition().distance(nextPosition);
//        
//        System.out.println("Enemy distance: " + distance);
//        
//        /*
//         * TODO: extract movement code and make separate class
//         */
//        
//        // If the distance between current position and the next is less than 
//        // ship's speed, move to next point and continue to the next saving 
//        // number of steps ship has left. This way the ship moves with a more
//        // even speed and it nullifies any rounding errors which may cause
//        // the ship to never reach next point exactly on the pixel.
//       if(distance <= SPEED){
//           setPosition(nextPosition);
//           System.out.println("Enemy: distance < SPEED");
//     //      remainingMoves = (int)(SPEED - distance);
//     //      System.out.println("Enemy: remaining moves = " + remainingMoves);
//       }
//        
//        // If ship is at the checkpoint, get next point to move to
//        // Calculate new dx and dy.
//        if(getPosition().equals(nextPosition)){
//            System.out.println("Enemy: position eq nextposition");
//            nextPosition = path.next();
//            
//            // If the previous position was the last to visit
//            // we are finished and should cease to exist.
//            if(nextPosition == null){
//                System.out.println("Enemy: nextPosition == null");
//                deactivate();
//                hide();
//                return;
//            }
//        }
//        
//       
//            // Diff between current and next coordinates
//            // diffX -> negative = ship is to the right
//            // diffY -> negative = ship is is below
//            diffX = nextPosition.getX() - getPositionX();
//            diffY = nextPosition.getY() - getPositionY();
//            System.out.println("Enemy: diffX = " + diffX);
//            System.out.println("Enemy: diffY = " + diffY);
//              
//            hypotenuse = getPosition().distance(nextPosition);
//            System.out.println("Enemy: hypotenuse = " + hypotenuse);
//            
//            // Calculate angle between ship's position and next point
//            angle = Math.acos(Math.abs(diffX) / hypotenuse);
//            System.out.println("Enemy: angle = " + Math.toDegrees(angle));
//         
//            // Calculate new dx and dy
//            double tempDx = 0;
//            double tempDy = 0;
//        
//            // We can only move SPEED units at a time so we calculate
//            // the x and y composition of the direction to move
//            // b = c * cos alfa
//            // a = c * sin alfa
//            tempDx = SPEED * Math.cos(angle);
//            tempDy = SPEED * Math.sin(angle);
//            System.out.println("Enemy: tempDx = " + tempDx);
//            System.out.println("Enemy: tempDy = " + tempDy);
//        
//            // If we had remaining moves left from last update we should
//            // add them to the distance we travel this time.
//         /*   if (remainingMoves > 0) {
//                System.out.println("Enemy: remainingMoves > 0");
//                tempDx += remainingMoves * Math.cos(angle);
//                tempDy += remainingMoves * Math.sin(angle);
//                System.out.println("Enemy: Adding remainingMoves tempDx = " + tempDx);
//                System.out.println("Enemy: Adding remainingMoves tempDy = " + tempDy);
//            }*/
//
//            // Conversion from double to int later gives rounding error.
//            // Add 0.5 to the result to make the errors smaller.
//            System.out.println("Enemy: adding +-0.5 to DX, DY");
//            
//            tempDx *= Math.signum(diffX);
//            tempDy *= Math.signum(diffY);
//            tempDx += (tempDx < 0 ? -0.5 : 0.5);
//            tempDy += (tempDy < 0 ? -0.5 : 0.5);
//         
//            System.out.println("Enemy: tempDx = " + tempDx);
//            System.out.println("Enemy: tempDy = " + tempDy);
//
//            // Normalize speed, dx and dy. This is where we could get
//            // loss off precision due to cast from double to int.
//            if (Math.abs(tempDx) > SPEED) {
//                System.out.println("Enemy: tempDx > SPEED");
//                setDx(SPEED * Math.signum(diffX));
//                System.out.println("Enemy: tempDx * Math.signum(diffX) = " + getDx());
//            } else {
//                System.out.println("Enemy: tempDx < SPEED");
//                setDx((int) tempDx);
//                System.out.println("Enemy: dx = " + getDx());
//            }
//            if (Math.abs(tempDy) > SPEED) {
//                System.out.println("Enemy: tempDy > SPEED");
//                setDy(SPEED * Math.signum(diffY));
//                System.out.println("Enemy: tempDy * Math.signum(diffY) = " + getDy());
//            } else {
//                System.out.println("Enemy: tempDy < SPEED");
//                setDy((int) tempDy);
//                System.out.println("Enemy: dy = " + getDy());
//            }
//        
//          
        super.update(); // Move the ship
        
        /*
         * We could add a timer here and make the ship fire it's weapon
         * at a given time interval or fire when it is a certain position
         * relative to player.
         * 
         */ 
            
    }
}
