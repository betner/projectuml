
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
//    private ArrayList<Ship> enemieswaiting;
    private int offset;
    
    /** Creates a new instance of Level */
    public Level() {
        playershots = new ArrayList<Shot>();
        enemyshots = new ArrayList<Shot>();
        enemies = new ArrayList<Ship>();
        //enemieswaiting = new ArrayList<Ship>();
        background = null;
        offset = 0;
    }

    /**
     * Gets the scenery background
     * @return
     */
    public Scenery getScenery() {
        return background;
    }

    /**
     * Changes the scenery background
     * @param background
     */
    public void setScenery(Scenery background) {
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
    public void update(PlayerShip player) {
        // Background
        if (background != null) {
            background.update();
        }

        // All the shots the player fires
        for (Shot shot : playershots) {
            shot.update();
            
            // Does it hit an enemy?
            for (Ship enemy : enemies) {
                if (shot.inShape(enemy.getPosition())) {
                    shot.touch(enemy);
                }
            }
        }
        
        // All the shots the enemies fires
        for (Shot shot : enemyshots) {
            shot.update();
            
            // Does it hit the player?
            if (shot.inShape(player.getPosition())) {
                shot.touch(player);
            }
        }
        
        // All the enemies
        for (Ship ship : enemies) {
            ship.update();
        }
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
     * Adds a shot from the player to the level world
     * @param shot
     */
    public void addPlayerShot(Shot shot) {
        if (shot != null) {
            playershots.add(shot);
        }
    }
    
    /**
     * Adds a shot from the enemy to the level world
     * @param shot
     **/
    public void addEnemyShot(Shot shot) {
        if (shot != null) {
            enemyshots.add(shot);
        }
    }
    
    /**
     * Adds an enemy ship to the level
     * @param ship
     **/
    public void addShip(Ship ship) {
        if (ship != null) {
            enemies.add(ship);
        }
    }
    
    /**
     * Clears out the level
     */
    public void removeAll() {
        enemies.clear();
        enemyshots.clear();
        playershots.clear();
        offset = 0;
        
        // Erase background scenery too?
    }
 
}
