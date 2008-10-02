
package projectuml;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * AnimatedSprite
 *
 * Subclass of Sprite that hold a list of images and draws them to screen 
 * in a set time interval.
 * All images should be added to imageList before it is activated.
 * By default Sprites are deactivated.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class AnimatedSprite extends Sprite {
   
   private static final long serialVersionUID = 1L;
   
   private Timestamp time; // Timer
   private long speed;     // Amount of time one image should be shown
   private int runtime;    // Total running time of animation
   private int sequence;   // Sequence number of image to draw
   private  Boolean repeat; // Should animation repeat
   private  Boolean done;   // Set to true when we don't repeat and we're done
   transient private ArrayList<BufferedImage> imageList;  // Images to animate
    
    
    /**
     * Create animated sprite and set default runtime
     */
    public AnimatedSprite(){
        this(1000); // Run time set to one second
    }
    
    /**
     * Create animated sprite and set default runtime and
     * make it not repeating
     *
     * @param runtime
     */
    public AnimatedSprite(int runtime){
        this(runtime, false); // Animation will not repeat
    }
    
    /**
     * Create animated sprite
     * 
     * @param runtime in milliseconds, repeat
     */
    public AnimatedSprite(int runtime, Boolean repeat){
        done = false;   // We haven't even started yet!
        this.runtime = runtime;
        this.repeat = repeat;
        sequence = 0;   // Init to first entry in imageList
        time = new Timestamp();
        speed = 0;
        imageList = new ArrayList<BufferedImage>();
        
        /*System.out.println("AnimatedSprite created:");
        System.out.println("=> Speed: " + speed);
        System.out.println("=> Done flag:" + done.toString());
        System.out.println("=> Runtime: " + runtime);*/
    }
    /**
     * Add image to the list of images to be animated
     * and update animation speed.
     */
    public void addImage(BufferedImage image){
        imageList.add(image);
        speed = runtime / imageList.size();   
        
        /*System.out.println("AnimatedSprite: addImage()");
        System.out.println("=> imageList.size(): " + imageList.size());
        System.out.println("=> runtime: " + runtime);
        System.out.println("=> New speed set to: " + speed);*/
    }

    /**
     * Update state of animation.
     * Set current image, Sprite's image, to the next
     * image in the sequence. 
     * If animation isn't looped future updates will be ignored
     * when we reach the end of the sequence.
     */
    public void update(){
        if(isActive()){
            // Current picture should be changed if the time
            // set in speed has passed since last update
            if(time.havePassed(speed)){ 
                setImage(imageList.get(sequence % imageList.size()));
                sequence++;   // Point to next image
                time.reset(); // Reset timestamp
                
                System.out.println("AnimatedSprite: update()");
                System.out.println("=> time passed");
            }
            // We've gone through the image sequence and should
            // not loop so make sure no image is displayed
            if(!repeat && sequence >= imageList.size()){
                hide();
                deactivate();
                done = true; // Animation is done!
                
                System.out.println("AnimatedSprite: done = true");
            }
        }
    }
   
    /**
     * Is animation done?
     *
     * @return true or false
     */
    public Boolean isDone(){
        return done;
    }
    
    /**
     * Reset animation
     */
    public void reset(){
        sequence = 0; // First image in sequence
        done = false; // 
        activate();   //
        show();
    }
    // DEPRECATED
    
    /**
     * Load images of given name to the list of images
     * that should be animated.
     * Animation files are named: path + name + number + .xyz
     * We get all files containing the string name. 
     * Then we get the sequencenumber from filename and
     * load that image into correct cell i the array.
     **/
    /*
    private void loadImages(String path, String name){
        if (path != null) {
            // Traverse the directory and search
            // for files corresponding to name variable
            char sequence; // the number part in the filename
            int index;     // index to store image in
            File directory = new File(path);
            if (directory.isDirectory()) {
                for (File file : directory.listFiles(new nameFilter(name))) {
                    sequence = file.getName().charAt(name.length());
                    try{
                     index = Integer.parseInt(sequence); 
                     imageList.add(index - 1, loadImage(path + file));
                    }
                    
                   
                }
            }
        }    
    }
    */
    
     /**
      * nameFilter
      *
      * Filter out image files that contains the name of
      * the requested animation. 
      * E.g  filename = path + name + number + .xyz
      *      filename = /resources/images/explosion1.png
      */
    /*
    private class nameFilter implements FilenameFilter {

        private String name;
        
        public nameFilter(String name) {
            this.name = name;
        }
        
        public boolean accept(File dir, String fileName) {
            return fileName.contains(this.name);
        }
    }*/
    // END DEPRECATED 
}
