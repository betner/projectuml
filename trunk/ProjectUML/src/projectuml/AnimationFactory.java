package projectuml;

import java.awt.image.BufferedImage;

/**
 * Factory for animations. The method's in the
 * factory are static, meaning there's no need
 * to instanciate the factory itself (which makes
 * sense since it doesn't hold any information
 * by itself)
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class AnimationFactory {
    
    /**
     * Creates a default explosion, running for
     * 500 ms
     * @return AnimatedSprite
     */
    public static AnimatedSprite createExplosion() {
        return AnimationFactory.createExplosion(500);
    }
    
    /**
     * Creates an explosion that runs for a
     * certain amount of time
     * @param time How long it should run
     * @return AnimatedSprite
     */
    public static AnimatedSprite createExplosion(int time) {
        AnimatedSprite sprite = new AnimatedSprite(time);
        // Try to load as many images as
        // possible on the format "explosionN.png"
        int i = 1;
        while (true) {
            BufferedImage image = sprite.loadImage("explosion" + i + ".png");
            if (image == null) {
                // Doesn't exist, break out
                break;
            } else {
                // Add it to the animation sequence
                // and continue
                sprite.addImage(image);
                i += 1;
            }
        }
        return sprite;
    }

}
