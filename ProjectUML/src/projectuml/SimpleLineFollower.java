/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projectuml;

import java.awt.Point;

/**
 * SimpleLineFollower
 * 
 * A simple navigator that follows the points in a path in a 
 * straight line.
 * Because a ship's position is represented as a point with
 * integer, non float values the trig function results are
 * cast and loose precision. That means that if the ship is 
 * going to have a chance to reach those points we have to 
 * recalculate a new line to follow every update.
 *
 * @see Navigator
 * @author Steve Eriksson, Jens Thuresson
 */
public class SimpleLineFollower extends Navigator {

    int maxMovement;
    Point destination;
    //Point currentPosition;
    //Point nextPosition;
    private double angle;
    private double hypotenuse;
    private double diffX;
    private double diffY;
    private int dx,  dy;

    /**
     * Create a SimpleLineFollower
     * A starting position have to be supplied to initiate the object.
     * 
     * @param start point
     */
    SimpleLineFollower(Point start) {
        destination = new Point();
        destination.setLocation(start); // start value
        //currentPosition = new Point();
        //currentPosition.setLocation(start);
    }

    /**
     * Set new destination. 
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

    /**
     * This is where the navigator calculates next x and y position the
     * ship should move to. 
     * Due to rounding errors in cast from double to int a new
     * line to follow has to be calculated every time the ship
     * requests a new point to get to.
     * 
     * @return nextPosition point
     */
    public Point getNextPosition(Point currentPosition) {
        double distance = currentPosition.distance(destination);//getPosition().distance(nextPosition);

        // If the distance between current position and the next is less than 
        // ship's speed, move to next point and continue to the next saving 
        // number of steps ship has left. This way the ship moves with a more
        // even speed and it nullifies any rounding errors which may cause
        // the ship to never reach next point exactly on the pixel.
        if (distance <= maxMovement) {
            return destination;
        }
        // Diff between current and next coordinates
        // diffX -> negative = destination is to the left
        // diffY -> negative = destination is above
        diffX = destination.getX() - currentPosition.getX();
        diffY = destination.getY() - currentPosition.getY();

        // Calculate angle between ship's position and next point
        angle = Math.acos(Math.abs(diffX) / distance);

        // Calculate new dx and dy
        double tempDx = 0;
        double tempDy = 0;

        // We can only move maxMovement units at a time so we calculate
        // the x and y composition of the direction to move
        // b = c * cos alfa
        // a = c * sin alfa
        tempDx = maxMovement * Math.cos(angle);
        tempDy = maxMovement * Math.sin(angle);

        // Conversion from double to int later gives rounding error.
        // Add 0.5 to the result to make the errors smaller.
        tempDx *= Math.signum(diffX);
        tempDy *= Math.signum(diffY);
        tempDx += (tempDx < 0 ? -0.5 : 0.5);
        tempDy += (tempDy < 0 ? -0.5 : 0.5);

        // Normalize speed, dx and dy. This is where we could get
        // loss off precision due to cast from double to int.
        if (Math.abs(tempDx) > maxMovement) {
            dx = (int) (maxMovement * Math.signum(diffX));
        } else {
            dx = (int) tempDx;
        }
        if (Math.abs(tempDy) > maxMovement) {
            dy = (int) (maxMovement * Math.signum(diffY));
        } else {
            dy = (int) tempDy;
        }

        Point nextPosition = new Point(currentPosition);
        nextPosition.translate(dx, dy);
        //currentPosition.setLocation(nextPosition);
        return nextPosition;
    }
    
}
