package projectuml;

import java.awt.Graphics2D;

/**
 * Represents a class that receives draw-calls
 * from the main rendering loop
 *
 * @author Jens Thuresson, Steve Eriksson
 **/
public interface DrawListener {
    public abstract void draw(Graphics2D g);
}
