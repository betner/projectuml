package projectuml;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * AnimatedSprite
 *
 * Subclass of Sprite that hold a list of images and draws them to screen 
 * in a given time interval.
 * All images should be added to imageList before it is activated.
 * By default Sprites are deactivated and hidden.
 * 
 * @see Sprite
 * @author Steve Eriksson, Jens Thuresson
 */
public class AnimatedSprite extends Sprite {

    private static final long serialVersionUID = 1L;
    private Timestamp time; // Timer
    private long speed;     // Amount of time one image should be shown
    private int runtime;    // Total running time of animation
    private int sequence;   // Sequence number of image to draw
    private Boolean repeat; // Should animation repeat
    private Boolean done;   // Set to true when we don't repeat and we're done
    transient private ArrayList<BufferedImage> imageList;  // Images to animate

    /**
     * Create animated sprite and set default runtime
     */
    public AnimatedSprite() {
        this(1000); // Run time set to one second
    }

    /**
     * Create animated sprite and set default runtime and
     * make it not repeating
     *
     * @param runtime
     */
    public AnimatedSprite(int runtime) {
        this(runtime, false); // Animation will not repeat
    }

    /**
     * Create animated sprite
     * 
     * @param runtime in milliseconds
     * @param repeat, true or false
     */
    public AnimatedSprite(int runtime, Boolean repeat) {
        done = false;
        this.runtime = runtime;
        this.repeat = repeat;
        sequence = 0; // Point to first image in sequence
        time = new Timestamp();
        speed = 0;
        imageList = new ArrayList<BufferedImage>();
    }

    /**
     * Add image to the list of images to be animated
     * and update the animation speed.
     */
    public void addImage(BufferedImage image) {
        imageList.add(image);
        speed = runtime / imageList.size();
    }

    /**
     * Update state of animation.
     * Change image if the configured time has passed.
     * If the animation isn't looped future updates will be 
     * ignored when it reaches the end of the sequence.
     * 
     * @param level reference which isn't used by thos object
     */
    public void update(Level level) {
        if (isActive()) {
           
            // Current picture should be changed if the time
            // set in speed has passed since last update
            if (time.havePassed(speed)) {
                setImage(imageList.get(sequence % imageList.size()));
                sequence++;   // Point to next image
                time.reset(); // Reset timestamp
            }
            // We've gone through the image sequence and should
            // not loop so make sure no image is displayed
            if (!repeat && sequence >= imageList.size()) {
                hide();
                deactivate();
                done = true; // Animation is done!
            }
        }
    }

    
    /**
     * Returns true if the animation is done.
     *
     * @return true or false
     */
    public Boolean isDone() {
        return done;
    }

    /**
     * Reset animation
     */
    public void reset() {
        sequence = 0; // First image in sequence
        done = false;
        activate();
        show();
    }
}
