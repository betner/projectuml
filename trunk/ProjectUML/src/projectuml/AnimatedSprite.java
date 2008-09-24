
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
 * @author Steve Eriksson, Jens Thuresson
 */
public class AnimatedSprite extends Sprite {
    
    Timestamp time; // Timer
    long speed;     // Animation speed
    int runtime;    // Total running time of animation
    int sequence;   // Sequence number of image to draw
    Boolean repeat; // Should animation repeat
    Boolean done;   // Set to true when we don't repeat and we're done
    ArrayList<BufferedImage> imageList;  // Images to animate
    
    
    /**
     * Create animated sprite with default values:
     */
    public AnimatedSprite(){
        repeat = false; // Animation will not repeat
        done = false;   // We haven't even started yet!
        sequence = 0;   // First entry in imageList
        time = new Timestamp(); 
        imageList = new ArrayList<BufferedImage>(); 
    }
    
    /**
     * Add image to the list of images to be animated
     * and update animation speed.
     */
    public void addImage(BufferedImage image){
        imageList.add(image);
        speed = runtime / imageList.size();   
    }

    public void update(){
        if(active){
            if(time.havePassed(speed)){
                image = imageList.get(sequence % imageList.size());
                sequence++;
            }
            if(!repeat && sequence >= imageList.size()){
                hide();
                deactivate();
                done = true; // Animation is done!
            }
        }
    }
   
    public Boolean isDone(){
        return done;
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
