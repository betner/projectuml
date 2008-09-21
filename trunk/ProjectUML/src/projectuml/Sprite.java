package projectuml;

import java.awt.*;
import java.awt.image.*;

/**
 * Sprite is an abstract class that contains methods for creating objects
 * that are shown on game screen.
 *
 * @author Steve Eriksson
 */
public abstract class Sprite {
    
    protected Point position;      // Objects upper left corner
    protected Boolean visible;     // TRUE = object is visible
    protected int width;
    protected int height;
    protected Image image;         // Graphic representing this object
    protected String imageFile;    // Path to sprite's image
    
    /** Creates a new instance of Sprite */
    public Sprite() {
    }
    
    /**
     * Update sprite. E.g set new position
     */
    abstract public void update();
    
    
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
