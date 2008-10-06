package projectuml;

import java.awt.Point;
import java.io.Serializable;

/**
 * Weapon
 * 
 * Weapons aren't represented as graphic objects onscreen like a Sprite. 
 * Weapons create shots in the level when fired. That means it has to 
 * have a reference to the current Level object. 
 * Shots originate from weapon's position and it's position must be translated
 * to the object who owns the weapons current position.
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
abstract public class Weapon implements Serializable {

    private String shotImageFile; // Path to the image representing a shot
    private Point position; // Weapon position
    private boolean player; // True if weapon is on player shipxs
    private String soundName; // Name of the sound to play

    /** 
     * Creates a new instance of Weapon 
     */
    public Weapon() {
    }

    /**
     * Fire weapon.
     *
     *@param game level
     */
    abstract public void fire(Level level, Sprite sprite);

    /**
     * Sets the player flag to true.
     */
    public void setPlayer(Boolean isPlayer) {
        player = isPlayer;
    }

    /**
     * Check if the weapon is a player weapon.
     * 
     * @return player
     */
    public Boolean isPlayer() {
        return player;
    }

    /**
     * Set weapon's position.
     * 
     * @param newPosition Point
     */
    public void setPosition(Point newPosition) {
        position = newPosition;
    }

    /**
     * Set weapon's position.
     * 
     * @param x double
     * @param y double
     */
    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    /**
     * Set weapon's position.
     * 
     * @param x integer
     * @param y integer
     */
    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    /**
     * Get weapon's position.
     * 
     * @return position Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set the image generated shots get.
     * 
     * @param path to image file
     */
    public void setShotImageFile(String path) {
        shotImageFile = path;
    }

    /**
     * Get the image used by shots.
     * 
     * @return shotImageFile
     */
    public String getShotImageFile() {
        return shotImageFile;
    }

    /**
     * Set the sound file used when weapon is fired.
     * 
     * @param path to sound file
     */
    public void setSoundFile(String path) {
        soundName = path;
    }

    /**
     * Get the name of the sound used by the weapon.
     * 
     * @return soundName
     */
    public String getSoundFile() {
        return soundName;
    }
}
