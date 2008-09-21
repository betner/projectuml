package projectuml;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO; // Faster image loading
import java.io.*;

/**
 * Sprite is an abstract class that contains methods for creating objects
 * that are shown on game screen.
 * The sprite has methods for painting itself onscreen, update its
 * state and position.
 *
 * @author Steve Eriksson
 */
public abstract class Sprite {
    
    protected Point position;      // Objects upper left corner
    protected Boolean visible;     // TRUE = object is visible
    protected int width;
    protected int height;
    protected BufferedImage image; // Graphic representing this object
    protected String imageFile;    // Path to sprite's image
    
    /** 
     * Creates a new instance of Sprite and load
     * the image representing it on screen.
     * The sprites width and height is set to match
     * the loaded image.
     */
    public Sprite(Point position, String imageFile) {
        this.position = position;
        image = loadImage(imageFile);
        height = image.getHeight();
        width = image.getWidth();
    }
    
    /**
     * Update sprite. E.g set new position
     */
    abstract public void update();
    
    /**
     * Load image asset and create a BufferedImage.
     * Make the image compatible to the graphics device
     * for hardware acceleration. This requires us to copy
     * the input image.
     * 
     * @param name of file to load
     */
    public BufferedImage loadImage(String imageFile){
        // Get device's graphics configuration
        GraphicsEnvironment ge;
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc;
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        
       try{
           BufferedImage bi, biCopy;
           bi = ImageIO.read(getClass().getResource(imageFile));
           
           // Get the image transparency information
           int transparency = bi.getColorModel().getTransparency();
           
           // Here is were the optimization takes place by making the 
           // image compatible with the device's graphics 
           biCopy = gc.createCompatibleImage(bi.getWidth(), bi.getHeight(), transparency);
                    
           // create a graphics context to draw on
           Graphics2D g2d = biCopy.createGraphics();
           
           // copy image 
           g2d.drawImage(bi, 0, 0, null);
           g2d.dispose();
           
           // Return the copied and optimized image
           return biCopy;
           
       }catch(IOException e){
           System.out.println("Image: " + imageFile +" not found!\n" + e);
           return null;
       }
             
    }
    
    /**
     * Draw the sprite to the screen
     */
    public void draw(Graphics2D g2d){
        g2d.drawImage(image, (int)position.getX(), (int)position.getY(), null);
    }
    
    /**
     * Functions to perform when object is touched.
     * Touch gets a reference to touching Sprite so
     * that we can alter its state if needed.
     */
    public void touch(Sprite s){}
    
    /**
     * Check if a point is within this objects boundries.
     * Default shape for a sprite is a rectangle.
     *
     * @param point
     * @return true or false
     **/
    public boolean inShape(Point p){
        if(p.getX() < position.getX() || p.getX() > position.getX() + width ){
            return false;
        }else if(p.getY() < position.getY() || p.getY() > position.getY() + height){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * Make object visible
     */
    public void show(){
        visible = true;
    }
    
    /**
     * Make object invisible
     */
    public void hide(){
        visible = false;
    }
    
/*
 * Getters and setters
 */
    
    public Point getPosition(){
        return position;
    }
    
    public void setPosition(Point newPosition){
        position = newPosition;
    }
}
