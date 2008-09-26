package projectuml;

import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * Scenery - beautiful backgrounds used in levels
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class Scenery implements Serializable {
    
    public abstract void update();
    public abstract void draw(Graphics2D g);
    
}
