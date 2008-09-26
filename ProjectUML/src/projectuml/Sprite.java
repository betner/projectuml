package projectuml;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO; 
import java.io.*;

/**
 * Sprite is a class that contains methods for creating objects
 * that are shown on game screen.
 * The sprite has methods for painting itself onscreen, update its
 * state and position.
 *
 * @author Steve Eriksson, Jens Thuresson
 */
public class Sprite {
    
    private Point position;      // Objects upper left corner
    private Boolean visible;     // TRUE = object performs draw()
    private Boolean active;      // TRUE = object performs update()
    private int width;           // Should be set if inShape is used
    private int height;
    private BufferedImage image; // Graphic representing this object
    private String imageFile;    // Path to image
    
    /** 
     * Creates a new instance of Sprite and load
     * the image representing it on screen.
     * Subclasses to Sprite should set width and height 
     * to match the loaded image.
     */
    public Sprite() {
        position = new Point(0,0); // If set here getPosition returns null!
        hide();
        deactivate();
        System.out.println("Sprite constructor");
        System.out.println(getPosition());
    }
    
    /**
     * Update sprite. E.g set new position
     */
    public void update(){
        
    }
    
    /**
     * Load image asset and create a BufferedImage.
     * Make the image compatible to the graphics device
     * for hardware acceleration. This requires us to copy
     * the input image.
     * 
     * @param absolute file path -> path/name.xyz
     */
    public BufferedImage loadImage(String file){
        // Get device's graphics configuration
        GraphicsEnvironment ge;
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsConfiguration gc;
        gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
        
       try{
           BufferedImage bi, biCopy;
           // If image is loaded from a jar-archive we need to treat the
           // file path as a URL
//           bi = ImageIO.read(getClass().getResource(imageFile));
           bi = ImageIO.read(new File(file));
           
           // Get the image transparency information
           int transparency = bi.getColorModel().getTransparency();
           
           // Here is were the optimization takes place by making the 
           // image compatible with the device's graphics 
           biCopy = gc.createCompatibleImage(bi.getWidth(), bi.getHeight(), transparency);
                    
           // Create a graphics context to draw on
           Graphics2D g2d = biCopy.createGraphics();
           
           // Copy image 
           g2d.drawImage(bi, 0, 0, null);
           g2d.dispose();
           
           // Return the copied and optimized image
           return biCopy;
           
       }catch(IOException e){
           System.out.println("Image: " + file +" not found!\n" + e);
           return null;
       }
             
    }
    
    /**
     * Draw the sprite to the screen.
     * if the visibility flag is set to true.
     * Position variable belongs to the objects that
     * calls draw().
     */
    public void draw(Graphics2D g2d){
        if(visible){
        g2d.drawImage(image, getIntPositionX(), getIntPositionY(), null);
        }
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
    
    /**
     * Make the object listen to update requests
     */
    public void activate(){
        active = true;
    }
    
    /**
     * Check if sprite is active
     * 
     * @return Boolean
     */
    public Boolean isActive(){
        return active;
    }
    
    /**
     * Stop object from performing update() method
     * when it gets a update request
     */
    public void deactivate(){
        active = false;
    }
    
    /**
     * Get sprite's position
     */
    public Point getPosition(){
        return this.position;
    }
    
    public int getIntPositionX(){
        return (int)position.getX();
    }
    
    public int getIntPositionY(){
        return (int)position.getY();
    }
    
    public double getPositionX(){
        return position.getX();
    }
    
    public double getPositionY(){
        return position.getY();
    }
    
    /**
     * Set new position for sprite
     * Three versions are provided with different argument types
     */
    public void setPosition(Point newPosition){
        position = newPosition;
    }
    
    public void setPosition(double x, double y){
        position.setLocation(x, y);
    }
    
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }
    
    /**
     * Get sprite's width
     * 
     * @return width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * Set sprite's width
     * 
     * @param newWidth
     */
    public void setWidth(int newWidth){
        width = newWidth;
    }
    
    /**
     * Get sprite's width
     * 
     * @return width
     */
    public int getHeight(){
        return height;
    }
    
    /**
     * Set sprite's height
     * 
     * @param newHeight
     */
    public void setHeight(int newHeight){
        height = newHeight;
    }
    
    /**
     * Get sprite's image
     * 
     * @return BufferedImage
     */
    public BufferedImage getImage(){
        return image;
    }
    
    /**
     * Set sprite's image
     * 
     * @param bufferedImage
     */
    public void setImage(BufferedImage bufferedImage){
        image = bufferedImage;
    }
    
    /**
     * Get the path to the image
     * 
     * @return String 
     */
    public String getImageFile(){
        return imageFile;
    }
    
    /**
     * Set path to the image
     * 
     * @param newImageFile
     */
    public void setImageFile(String newImageFile){
        imageFile = newImageFile;
    }
    
}
