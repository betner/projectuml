
package projectuml;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * A path that a ship follows
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Path implements Serializable {
    
    private ArrayList<Point> pathlist;
    private boolean cyclic;
    private int current;

    /**
     * Creates an empty path
     **/
    public Path(boolean cyclic) {
        pathlist = new ArrayList<Point>();
        current = -1;
        this.cyclic = cyclic;
    }
    
    /**
     * Retrieves the next point on the path
     * @return Point
     **/
    public Point next() {
        if (cyclic) {
            current = (current + 1) % pathlist.size();
        } else {
            current += 1;
        }
        if (current < pathlist.size()) {
            return pathlist.get(current);
        } else {
            return null;
        }
    }
    
    /**
     * Adds a point to the path
     * @param point
     **/
    public void addPoint(Point point) {
        pathlist.add(point);
    }
    
    /**
     * Removes the last point in the list
     **/
    public void removeLast() {
        if (!pathlist.isEmpty()) {
            pathlist.remove(pathlist.size()-1);
        }
    }
    
    /**
     * Resets counter and restarts at the beginning
     * again
     */
    public void reset() {
        current = -1;
    }
    
    /**
     * @return True if it's cyclic
     */
    public boolean isCyclic() {
        return cyclic;
    }
    
    /**
     * Turns cyclic property on or off
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }
    
    /**
     * Removes all points from the path
     **/
    public void removeAll() {
        pathlist.clear();
    }
    
    /**
     * Moves the path as a whole a certain amount
     * @param deltax How many points to move on the x-axis
     * @param deltay How many points to move on the y-axis
     */
    public synchronized void translate(int deltax, int deltay) {
        for (Point point : pathlist) {
            point.translate(deltax, deltay);
        }
    }
    
    public static void main(String[] args) {
        Path path = new Path(false);
        path.addPoint(new Point(0, 0));
        path.addPoint(new Point(10, 10));
        path.addPoint(new Point(40, 0));
        path.addPoint(new Point(-10, 200));
        
        while (true) {
            Point p = path.next();
            if (p == null)
                break;
            System.out.println(p);
        }
    }
    
}
