
package projectuml;

import java.awt.Point;

/**
 * Weapon
 * 
 * Weapons aren't represented as graphic objects onscreen like a Sprite. 
 * Weapons create shots in the level when fired. That means it has to 
 * have a reference to the current Level object. 
 * Shots originate from weapon's position.
 * Depending on if the weapon is mounted on a player or enemy ship
 * the shots gets created differently.
 * It is the weapon that sets the Shot's parameters such as
 * direction and image.
 * When weapon is fired it calls the level and asks it to play
 * the file set in soundName variable.
 *
 * @see Shot
 * @author Steve Eriksson, Jens Thuresson
 */
abstract public class Weapon {
    
    private String shotImageFile; // Path to the image representing a shot
    private Point position; // Weapon position
    private boolean player; // True if weapon is on player ship
    private SoundPlayer sound; 
    private String soundName; // Name of the sound to play
           
    /** Creates a new instance of Weapon */
    public Weapon() {
    }
    
    /**
     * Fire weapon
     *
     *@param game level
     */
    abstract public void fire(Level level);
    
    //DEBUG Method
    abstract public void fire(TestDrive td);
   
    abstract public void playSound();
    
    /**
     *  Initialize the soundplayer
     */
    public void initSound(){
       // sound = new SoundPlayer();
    }

    /**
     * Sets the player flag to true
     */
    public void setPlayer(Boolean isPlayer){
        player = isPlayer;
    }
    public Boolean isPlayer(){
        return player;
    }
    
    public void setPosition(Point newPosition){
        position = newPosition;
    }
    
    public void setPosition(double x, double y){
        position.setLocation(x, y);
    }
    
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }
    
    // Jens: added
    public Point getPosition() {
        return position;
    }
    
    public Point clone(Point position){
        Point clone = new Point();
        clone.setLocation(position);
        return clone;
    }
    
    public void setShotImageFile(String path){
        shotImageFile = path;
    }
    
    public String getShotImageFile(){
        return shotImageFile;
    }

    public void setSoundFile(String path){
        soundFile = path;
    }
    
    public String getSoundFile(){
        return soundFile;
    }
}
