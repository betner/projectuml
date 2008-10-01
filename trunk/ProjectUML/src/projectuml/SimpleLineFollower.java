/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectuml;

import java.awt.Point;

/**
 *
 * @author steeri
 */
public class SimpleLineFollower extends Navigator {

    int maxMovement;
    Point destination;
    Point currentPosition;
    Point nextPosition;
    private double angle;
    private double hypotenuse;
    private double diffX;
    private double diffY;
    private int dx,  dy;

    /**
     * Create a SimpleLineFollower
     * A starting position have to be supplied to initiate the object
     */
    SimpleLineFollower(Point start) {
        System.out.println("Route " + start);
        nextPosition = new Point();
        destination = new Point();
        destination.setLocation(start); // start value
        currentPosition = new Point();
        currentPosition.setLocation(start);
        System.out.println("SimpleLineFollower: currentPosition: " + currentPosition);
    }

    /**
     * Set new destination 
     * 
     * @param Point p
     */
    public void newDestination(Point p) {
        destination.setLocation(p);
    }

    /**
     * Set the maximum steps between positions to generate.
     * 
     * @param integer m
     */
    public void setMaxMovement(int m) {
        maxMovement = m;
    }

    public Point getNextPosition() {
        System.out.println("getNextPosition():");
        System.out.println("currentPosition = " + currentPosition);
        System.out.println("destination = " + destination);
        double distance = currentPosition.distance(destination);//getPosition().distance(nextPosition);

        System.out.println("Route: distance: " + distance);

        // If the distance between current position and the next is less than 
        // ship's speed, move to next point and continue to the next saving 
        // number of steps ship has left. This way the ship moves with a more
        // even speed and it nullifies any rounding errors which may cause
        // the ship to never reach next point exactly on the pixel.
        if (distance <= maxMovement) {
            System.out.println("Route: distance <= maxMovement");
            nextPosition.setLocation(destination);
            currentPosition.setLocation(destination);
            System.out.println("nextPosition = " + nextPosition);
            return nextPosition;
        }
        // Diff between current and next coordinates
        // diffX -> negative = destination is to the left
        // diffY -> negative = destination is above
        diffX = destination.getX() - currentPosition.getX();
        diffY = destination.getY() - currentPosition.getY();
        System.out.println("Route: diffX = " + diffX);
        System.out.println("Route: diffY = " + diffY);

        //hypotenuse = distance;
        System.out.println("Route: hypotenuse = " + distance);

        // Calculate angle between ship's position and next point
        angle = Math.acos(Math.abs(diffX) / distance);
        System.out.println("Route: angle = " + Math.toDegrees(angle));

        // Calculate new dx and dy
        double tempDx = 0;
        double tempDy = 0;

        // We can only move maxMovement units at a time so we calculate
        // the x and y composition of the direction to move
        // b = c * cos alfa
        // a = c * sin alfa
        tempDx = maxMovement * Math.cos(angle);
        tempDy = maxMovement * Math.sin(angle);
        System.out.println("Route: tempDx = " + tempDx);
        System.out.println("Route: tempDy = " + tempDy);


        // Conversion from double to int later gives rounding error.
        // Add 0.5 to the result to make the errors smaller.
        System.out.println("Enemy: adding +-0.5 to DX, DY");

        tempDx *= Math.signum(diffX);
        tempDy *= Math.signum(diffY);
        tempDx += (tempDx < 0 ? -0.5 : 0.5);
        tempDy += (tempDy < 0 ? -0.5 : 0.5);

        System.out.println("Enemy: tempDx = " + tempDx);
        System.out.println("Enemy: tempDy = " + tempDy);

        // Normalize speed, dx and dy. This is where we could get
        // loss off precision due to cast from double to int.
        if (Math.abs(tempDx) > maxMovement) {
            System.out.println("Route: tempDx > SPEED");
            dx = (int) (maxMovement * Math.signum(diffX));
            System.out.println("Route: tempDx * Math.signum(diffX) = " + dx);
        } else {
            System.out.println("Route: tempDx < maxMovement");
            dx = (int) tempDx;
            System.out.println("Route: dx = " + dx);
        }
        if (Math.abs(tempDy) > maxMovement) {
            System.out.println("Enemy: tempDy > SPEED");
            dy = (int) (maxMovement * Math.signum(diffY));
            System.out.println("Enemy: tempDy * Math.signum(diffY) = " + dy);
        } else {
            System.out.println("Route: tempDy < maxMovement");
            dy = (int) tempDy;
            System.out.println("Route: dy = " + dy);
        }

        nextPosition.translate(dx, dy);
        currentPosition.setLocation(nextPosition);
        System.out.println("End of getNextPosition()");
        return nextPosition;
    }
}
