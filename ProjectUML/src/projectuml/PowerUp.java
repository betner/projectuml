package projectuml;

import java.awt.Point;

/**
 * PowerUp
 *
 * PowerUp is a pickable sprite that gives a player some sort of bonus.
 * 
 * @see Sprite
 * @author Steve Eriksson, Jens Thuresson
 */
public class PowerUp extends Sprite {

    private int offset; // Needed?

    /**
     * Creates a new instance of PowerUp 
     * 
     * @param position
     * @imageFile path to image
     */
    public PowerUp(Point position, String imageFile) {
        setPosition(position);
        setImage(loadImage(imageFile));
        activate();
        show();
    }

    /**
     * Overridden touch method. We want power up's to dissapear
     * after they are picked up.
     * PowerUp must be visible and active to bless player with it's goodies.
     * 
     * @param sprite
     */
    public void touch(Sprite sprite) {
        if (isActive() && isVisible()) {
            getTouchBehaviour().action(sprite);
            deactivate();
            hide();
        }
    }
}
