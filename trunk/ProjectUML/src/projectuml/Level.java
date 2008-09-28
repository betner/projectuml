
package projectuml;

import java.util.*;
import java.awt.*;
import java.io.*;

/**
 * A level in the game
 * 
 * @author Steve Eriksson, Jens Thuresson
 */
public class Level implements Serializable {
    
    private static final long serialVersionID = 1L;
    transient private SoundPlayer soundplayer;
    private Scenery background;
    private ArrayList<Shot> playershots;
    private ArrayList<Shot> enemyshots;
    private ArrayList<EnemyShip> enemies;
    private int offset;
    
    /** Creates a new instance of Level */
    public Level() {
        playershots = new ArrayList<Shot>();
        enemyshots = new ArrayList<Shot>();
        enemies = new ArrayList<EnemyShip>();
        background = null;
        offset = 0;
        soundplayer = new SoundPlayer();
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
    public synchronized void update(PlayerShip player) {
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
        for (EnemyShip ship : enemies) {
            ship.update();
        }
    }
    
    /**
     * Draws the level on the screen
     * @param g
     */
    public synchronized void draw(Graphics2D g) {
        // Only draw a scenery if we got one!
        if (background != null) {
            background.draw(g);
        }
        
        // Draw the enemies
        for (EnemyShip ship : enemies) {
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
            // FIX: all sprites should be visible by default!
            shot.show();
            playershots.add(shot);
        }
    }
    
    /**
     * Adds a shot from the enemy to the level world
     * @param shot
     **/
    public void addEnemyShot(Shot shot) {
        if (shot != null) {
            // FIX: see above
            shot.show();
            enemyshots.add(shot);
        }
    }
    
    /**
     * Adds an enemy ship to the level
     * @param ship
     **/
    public void addShip(EnemyShip ship) {
        if (ship != null) {
            enemies.add(ship);
        }
    }
    
    /**
     * Removes an enemy ship at a certain position
     * @param point
     **/
    public void removeShipAt(Point point) {
        EnemyShip marked = getShipAt(point);
                
        // Did we find anyone?
        if (marked != null) {
            enemies.remove(marked);
        }
    }
    
    /**
     * TODO: can we allow this kind of methods?
     * @param point
     * @return EnemyShip at the position, or null
     **/
    public EnemyShip getShipAt(Point point) {
        for (EnemyShip ship : enemies) {
            if (ship.inShape(point)) {
                return ship;
            }
        }
        // None found
        return null;
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
    
    /**
     * Loads a sound. It's keyname will be the
     * simple filename without extensions
     * (e.g. "/misc/sounds/pang.wav" becomes "pang")
     * @param filename Path to soundfile (wav)
     **/
    public void loadSound(String filename) {
        soundplayer.loadSound(filename);
    }
    
    /**
     * Plays a previously loaded sound
     * @param keyname Key name of the loaded sound
     **/
    public void playSound(String keyname) {
        soundplayer.play(keyname);
    }
    
    /**
     * Does a normal serialization of the object
     **/
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    
    /**
     * Does a normal serialization of the object AND restarts
     * the sound player
     **/
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        soundplayer = new SoundPlayer();
        
        // TODO: should we reset the offset here? Every new level
        //       we start should start at offset zero, but if we forget
        //       to zero it out in the leveleditor it will be at that
        //       last offset.
        offset = 0;
    }
 
}
