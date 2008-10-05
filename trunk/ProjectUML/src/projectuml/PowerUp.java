
package projectuml;

import java.awt.Point;

/**
 *
 * @author Steve Eriksson
 */
public class PowerUp extends Sprite{
    
    
    private int offset;
    
    /** Creates a new instance of PowerUp */
    public PowerUp(Point position, String imageFile) {
        setPosition(position);
        setImage(loadImage(imageFile));
        activate();
        show();
    }
    
    /**
     * Overridden touch method. We want power up's to dissapear
     * after they are picked up.
     */
    public void touch(Sprite sprite){
       getTouchBehaviour().action(sprite);
       deactivate();
       hide();
    }
}
