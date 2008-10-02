
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
    
    private static final long serialVersionUID = 1L;
    private Scenery background;
    private ArrayList<Shot> playershots;
    private ArrayList<Shot> enemyshots;
    private ArrayList<EnemyShip> enemies;
    private int offset;
    private boolean editormode;
    
    // These are transient, meaning that they
    // aren't going to get serialized
    transient private SoundPlayer soundplayer;
    transient private Font font;
    
    /** Creates a new instance of Level */
    public Level() {
        playershots = new ArrayList<Shot>();
        enemyshots = new ArrayList<Shot>();
        enemies = new ArrayList<EnemyShip>();
        background = null;
        offset = 0;
        soundplayer = new SoundPlayer(".");
        editormode = false;
        font = new Font("Courier New", Font.PLAIN, 10);
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
     * Desides whether we're in level editor mode,
     * or not. When we're in level editor mode, we're
     * going to display more information when we draw
     * our scene
     * @param mode Set to true to turn level editor mode on
     */
    public void setEditorMode(boolean mode) {
        editormode = mode;
    }
    
    /**
     * @see setEditorMode
     * @return True if we're in level editor mode
     */
    public boolean inEditorMode() {
        return editormode;
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
                // TODO: only update enemies with >= offset
                if (enemy.inShape(shot.getPosition())) {
                    shot.touch(enemy);
                    // No need to check for additional
                    // hits
                    break;
                }
            }
        }
        
        // All the shots the enemies fires
        for (Shot shot : enemyshots) {
            shot.update();
            
            // Does it hit the player?
            if (player.inShape(shot.getPosition())) {
                shot.touch(player);
            }
        }
        
        // All the enemies
        for (EnemyShip ship : enemies) {
            // TODO: only update enemies with >= offset
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
            // TODO: only draw enemies with >= offset
            ship.draw(g);
            
            // Draw more info if we're in editor mode
            if (editormode) {
                g.setColor(Color.red);
                g.setFont(font);
                g.drawRect(ship.getIntPositionX(), ship.getIntPositionY(),
                        ship.getWidth(), ship.getHeight());
                Integer health = new Integer(ship.getHealth());
                g.drawString("Health: " + health, ship.getIntPositionX(), ship.getIntPositionY());
            }
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
        background = null;
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
     * Loop plays a previously loaded sound
     * @param keyname Key name of the loaded sound
     */
    public void loopSound(String keyname) {
        soundplayer.loopPlay(keyname);
    }
    
    /**
     * Stops a particular sound from playing
     * @param keyname Key name of the loaded sound
     */
    public void stopSound(String keyname) {
        soundplayer.stop(keyname);
    }
    
    /**
     * Stops all sounds!
     **/
    public void stopSound() {
        soundplayer.mute();
        soundplayer.unmute();
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
        
        // Recreate our transient objects since
        // our constructor doesn't get called
        soundplayer = new SoundPlayer(".");
        font = new Font("Courier New", Font.PLAIN, 10);
        
        // TODO: should we reset the offset here? Every new level
        //       we start should start at offset zero, but if we forget
        //       to zero it out in the leveleditor it will be at that
        //       last offset.
        offset = 0;
        
        // We always start in normal mode (as in NOT level editor mode)
        editormode = false;
    }
 
}
