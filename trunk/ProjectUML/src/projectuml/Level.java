
package projectuml;

import java.util.*;
import java.awt.*;
import java.io.Serializable;

/**
 * A level in the game
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class Level implements Serializable {
    
    private Scenery background;
    private ArrayList<Shot> playershots;
    private ArrayList<Shot> enemyshots;
    private ArrayList<Ship> enemies;
    private ArrayList<Ship> enemieswaiting;
    private int offset;
    
    /** Creates a new instance of Level */
    public Level() {
        playershots = new ArrayList<Shot>();
        enemyshots = new ArrayList<Shot>();
        enemies = new ArrayList<Ship>();
        enemieswaiting = new ArrayList<Ship>();
        background = null;
        offset = 0;
    }

    /**
     * Gets the scenery background
     * @return
     */
    public Scenery getBackground() {
        return background;
    }

    /**
     * Changes the scenery background
     * @param background
     */
    public void setBackground(Scenery background) {
        this.background = background;
    }
    
    /**
     * Travel forward
     * @param amount
     */
    public void increaseOffset(int amount) {
        offset += amount;
    }
    
    /**
     * Travel backwards (at least til zero)
     * @param amount
     */
    public void decreaseOffset(int amount) {
        offset -= amount;
        if (offset < 0) {
            offset = 0;
        }
    }
    
    /**
     * @return The current offset
     */
    public int getOffset() {
        return offset;
    }
    
    /**
     * Updates the level, handle collision detection and so on
     * @param player
     */
    public void update(Player player) {
        // TODO: update shot positions first, then handle collision?
        // Or both at the same time?
        
        // Regular updates
        for (Shot shot : playershots) {
            shot.update();
        }
        
        for (Shot shot : enemyshots) {
            shot.update();
        }
        
        for (Ship ship : enemies) {
            ship.update();
        }
        
        // Handle collision
        
    }
    
    /**
     * Draws the level on the screen
     * @param g
     */
    public void draw(Graphics2D g) {
        // Only draw a scenery if we got one!
        if (background != null) {
            background.draw(g);
        }
        
        // Draw the enemies
        for (Ship ship : enemies) {
            ship.draw(g);
        }
        
        // Draw the enemy bullets
        for (Shot shot : enemyshots) {
            shot.draw(g);
        }
        
        // Draw the player's bullets
        for (Shot shot : playershots) {
            shot.draw(g);
        }
    }
    
    /**
     * Adds a shot to the level world
     * @param shot
     */
    public void addShot(Shot shot){
        if (shot != null) {
            // TODO: shot comes from enemy or player?
        }
    }
    
    /**
     * Clears out the level
     */
    public void removeAll() {
        enemies.clear();
        enemieswaiting.clear();
        enemyshots.clear();
        playershots.clear();
        
        // Erase background scenery too?
    }
 
}
