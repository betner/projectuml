
package projectuml;

import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * A path that a ship follows. A cyclic path is
 * a path that returns to the first point when it
 * reaches the end.
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class Path implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> pathlist;
    private boolean cyclic;
    private int current;

    /**
     * Creates an empty path
     * @param cyclic True if the path should be cyclic
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
            // TODO: still return null, but we must change in
            // EnemyShip
            //return new Point(0, 0);
        }
    }
    
    /**
     * Adds a point to the path
     * @param point Position
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
     * Resets counter and (re)starts at the beginning
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
     * @param cyclic True if the path should be cyclic
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
    
    /**
     * Helper function to create a path that cycles
     * around 0, 0
     * @return Path
     */
    public static Path create() {
        return Path.create(0, 0);
    }
    
    /**
     * Helper function to create a path that cycles
     * on a single point
     * @param x
     * @param y
     * @return Path
     */
    public static Path create(int x, int y) {
        Path path = new Path(true);
        path.addPoint(new Point(x, y));
        return path;
    }
    
    /**
     * Does a normal serialization
     * @param out Stream to write to
     * @throws java.io.IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    /**
     * Does a normal serialization AND resets the current postition
     * @param in Stream to read from
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        reset();
    }
    
}
