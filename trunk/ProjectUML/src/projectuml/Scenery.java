package projectuml;

import java.awt.Graphics2D;

/**
 * Scenery - beautiful backgrounds used in levels
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class Scenery {
    
    public abstract void update();    
    public abstract void draw(Graphics2D g);
    
}
