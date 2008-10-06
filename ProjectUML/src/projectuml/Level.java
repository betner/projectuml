
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
    private Vector<Shot> playershots;
    private Vector<Shot> enemyshots;
    private Vector<EnemyShip> enemies;
    private Vector<Sprite> pickables;
    private int offset;
    private boolean editormode;
    private int health;
    private int maxhealth;
    
    // These are transient, meaning that they
    // aren't going to get serialized
    transient private SoundPlayer soundplayer;
    transient private Font font;
    transient private Font healthfont;
    
    /** Creates a new instance of Level */
    public Level() {
        playershots = new Vector<Shot>();
        enemyshots = new Vector<Shot>();
        enemies = new Vector<EnemyShip>();
        pickables = new Vector<Sprite>();
        background = null;
        offset = 0;
        editormode = false;
        health = 0;
        maxhealth = 0;
        initTransientObjects();
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
     * @param amount How much
     */
    public void increaseOffset(int amount) {
        offset += amount;
    }
    
    /**
     * Travel backwards (at least til zero)
     * @param amount How much
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
     * Sets the offset explicitly
     * @param amount How much
     **/
    public void setOffset(int offset) {
        this.offset = offset;
        if (this.offset < 0) {
            this.offset = 0;
        }
    }
    
    /**
     * Updates the level, handle collision detection and so on
     * @param player Active player ship
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
            for (EnemyShip enemy : enemies) {
                if (offset >= enemy.getOffset()) {
                    if (!enemy.isDestroyed() && enemy.inShape(shot.getPosition())) {
                        if (shot.isActive()) {
                            shot.touch(enemy);
                            shot.deactivate();
                            shot.hide();
                        }
                        break;
                    }
                }
            }
            
            // Is it off-screen?
            if (shot.getIntPositionX() > 640 || shot.getIntPositionY() > 480) {
                shot.deactivate();
                shot.hide();
            }
        }
        
        // All the shots the enemies fires
        for (Shot shot : enemyshots) {
            shot.update();
            
            // Does it hit the player?
            if (!player.isDestroyed() && player.inShape(shot.getPosition())) {
                if (shot.isActive()) {
                    shot.touch(player);
                    shot.deactivate();
                    shot.hide();
                }
            }
        }
        
        // All the enemies
//        Rectangle playerrect = new Rectangle(player.getIntPositionX(),
//                player.getIntPositionY(),
//                player.getWidth(),
//                player.getHealth());
        
        for (EnemyShip ship : enemies) {
            if (offset >= ship.getOffset()) {
                ship.update(this);
                
                // Does the player crash inside the
                // enemy?
//                Rectangle enemyrect = new Rectangle(ship.getIntPositionX(),
//                        ship.getIntPositionY(),
//                        ship.getWidth(),
//                        ship.getHeight());
//                if (playerrect.intersects(enemyrect)) {
                if (!ship.isDestroyed()) {
                    if (player.inShape(ship.getPosition())) {
                        player.destroyShip();
                        break;
                    }
                }
            }
        }
        
        // Update all pickables!
        for (Sprite sprite : pickables) {
            sprite.update(this);
            
            // Do we touch it?
            if (player.inShape(sprite.getPosition())) {
                sprite.touch(player);
            }
        }
        
        // Store away player lifes
        health = player.getHealth();
        maxhealth = player.getMaxHealth();
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
        
        // Draw the enemies that's at least
        // in our offset
        for (EnemyShip ship : enemies) {
            if (offset >= ship.getOffset()) {
                ship.draw(g);
                
                // Draw more info if we're in editor mode
                Integer health = new Integer(ship.getHealth());
                if (editormode) {
                    g.setColor(Color.red);
                    g.setFont(font);
                    g.drawRect(ship.getIntPositionX(), ship.getIntPositionY(),
                            ship.getWidth(), ship.getHeight());
                    g.drawString("Health: " + health, ship.getIntPositionX(), ship.getIntPositionY());
                    
                    Integer off = new Integer(ship.getOffset());
                    g.drawString("Offset: " + off, ship.getIntPositionX(), ship.getIntPositionY() + ship.getHeight() + font.getSize());
                } else {
                    // Just draw health
                    if (!ship.isDestroyed()) {
                        float percent = ship.getHealth() / (float)ship.getMaxHealth();
                        if (percent > 0.5) {
                            g.setColor(Color.green);
                        } else {
                            g.setColor(Color.red);
                        }
                        g.fillRect(ship.getIntPositionX(), ship.getIntPositionY()+ship.getHeight(), (int)(percent*ship.getWidth()), 2);
                    }
                }
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
        
        // Draw the pickables
        for (Sprite sprite : pickables) {
            sprite.draw(g);
        }
        
        // Don't draw the HUD if we're in
        // editor mode, because it isn't of
        // importance there
        if (!editormode) {
            drawHUD(g);
        }
    }
    
    /**
     * Draws the heads-up-display (info about player health)
     **/
    private synchronized void drawHUD(Graphics2D g) {
        float percent = 0f;
        if (maxhealth > 0) {
            percent = health / (float)maxhealth;
        }

        final int startx = 20;
        final int starty = 20;
        final int border = 2;
        final int height = 5;

        g.setColor(Color.green);
        g.setFont(healthfont);
        g.drawString("Life", startx, starty-border);
        g.drawLine(startx, starty-border, 640-startx, starty-border);
        if (percent > 0.5) {
            g.setColor(new Color(0, 128, 0));
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(startx, starty, (int)((640-startx-startx)*percent), height);
    }
    
    /**
     * Adds a shot from the player to the level world
     * @param shot
     */
    public synchronized void addPlayerShot(Shot shot) {
        if (shot != null) {
            shot.show();
            shot.activate();
            playershots.add(shot);
        }
    }
    
    /**
     * Adds a shot from the enemy to the level world
     * @param shot
     **/
    public synchronized void addEnemyShot(Shot shot) {
        if (shot != null) {
            shot.show();
            shot.activate();
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
     * Adds a pickable object (i.e. PowerUps)
     * @param sprite
     **/
    public void addPickable(Sprite sprite) {
        if (sprite != null) {
            pickables.add(sprite);
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
     * Returns the ship at the specified position, if
     * it's visible in the current offset
     * @param point Expected point
     * @return EnemyShip at the position, or null
     **/
    public EnemyShip getShipAt(Point point) {
        for (EnemyShip ship : enemies) {
            if (offset >= ship.getOffset() && ship.inShape(point)) {
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
     * Recreates transient objects
     */
    private void initTransientObjects() {
        font = new Font("Courier New", Font.PLAIN, 10);
        healthfont = new Font("Arial", Font.PLAIN, 12);
        soundplayer = new SoundPlayer(".");
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
        initTransientObjects();
        
        // TODO: should we reset the offset here? Every new level
        //       we start should start at offset zero, but if we forget
        //       to zero it out in the leveleditor it will be at that
        //       last offset.
        offset = 0;
        
        // We always start in normal mode (as in NOT level editor mode)
        editormode = false;
    }
    
}
