
package projectuml;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The state that represents a running game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class GameRunning extends GameState {
    
    // Time value in milliseconds for an offset
    private final long OFFSET_PERIOD = 1000;
    
    private GeneralSerializer<Level> levelloader;
    private Level currentlevel;
    private PlayerShip playership;
    private Font font;
    private enum GameCommandID {
        GO_UP, GO_DOWN, GO_LEFT, GO_RIGHT,
        FIRE_WEAPON,
        PAUSE,
        START_LEVEL_EDITOR
    };
    private Hashtable<Integer, GameCommandID> keys;
    private Timer timer;
    private boolean active;
    
    /**
     * Creates the game and associates it with
     * a certain player
     * @param player
     **/
    public GameRunning(Player player)  {
        playership = new PlayerShip(player);
        
        // Load a level
        levelloader = new GeneralSerializer<Level>();
        currentlevel = levelloader.load("level1.level");
        
        // Get a font
        font = new Font("Courier New", Font.PLAIN, 10);
        
        // Create our timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new OffsetTask(), 0, OFFSET_PERIOD);
        active = true;
        
        keys = new Hashtable<Integer, GameCommandID>();
        bindKeys();
        
        // TODO: do we choose our own level?
        //       or should we use a level-factory
        //       that reads in all available level files
        //       and then selects one for us?
    }
    
    /**
     * Binds specific game keys to certain commands
     **/
    private void bindKeys() {
        keys.put(KeyEvent.VK_LEFT, GameCommandID.GO_LEFT);
        keys.put(KeyEvent.VK_RIGHT, GameCommandID.GO_RIGHT);
        keys.put(KeyEvent.VK_UP, GameCommandID.GO_UP);
        keys.put(KeyEvent.VK_DOWN, GameCommandID.GO_DOWN);
        keys.put(KeyEvent.VK_P, GameCommandID.PAUSE);
        keys.put(KeyEvent.VK_PAUSE, GameCommandID.PAUSE);
        keys.put(KeyEvent.VK_F12, GameCommandID.START_LEVEL_EDITOR);
        keys.put(KeyEvent.VK_SPACE, GameCommandID.FIRE_WEAPON);
    }
    
    /**
     * Updates the world
     * @param player
     **/
    public void update(Player player) {
        if (currentlevel != null) {
            currentlevel.update(playership);
        }
        playership.update();
        restrictPlayerShip();
    }
    
    /**
     * Repaints the running game
     * @param g
     **/
    public synchronized void draw(Graphics2D g) {
        if (currentlevel != null) {
            currentlevel.draw(g);
        }
        playership.draw(g);
        
        if (currentlevel != null) {
            // Print out debug information
            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Offset: " + currentlevel.getOffset(), 0, 460);
        }
    }
    
    /**
     * Respond to key events, e.g. most importantly moving
     * the player ship
     * @param event
     **/
    public void keyEvent(KeyEvent event, boolean down) {
        // Is it a game command?
        if (keys.containsKey(event.getKeyCode())) {
            GameCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case GO_UP:
                    if (down) {
                        playership.goUp();
                    } else {
                        playership.resetDy();
                    }
                    break;
                    
                case GO_DOWN:
                    if (down) {
                        playership.goDown();
                    } else {
                        playership.resetDy();
                    }
                    break;
                    
                case GO_RIGHT:
                    if (down) {
                        playership.goRight();
                    } else {
                        playership.resetDx();
                    }
                    break;
                    
                case GO_LEFT:
                    if (down) {
                        playership.goLeft();
                    } else {
                        playership.resetDx();
                    }
                    break;
                    
                case FIRE_WEAPON:
                    playership.fire(currentlevel);
                    break;
                    
                case PAUSE:
                    getGameStateManager().push(new Pause());
                    break;
                    
                case START_LEVEL_EDITOR:
                    getGameStateManager().push(new LevelEditor(currentlevel));
                    break;
                    
                default:
                    break;
            }
        }
    }
    
    /**
     * Ensures the player ship isn't going off-screen
     **/
    private void restrictPlayerShip() {
        final double MAX_X = 640 - playership.getWidth();
        final double MAX_Y = 480 - playership.getHeight();
        
        Point pos = playership.getPosition();
        if (pos.getX() <= 0 || pos.getX() >= MAX_X) {
            playership.resetDx();
        }
        
        if (pos.getY() <= 0 || pos.getY() >= MAX_Y) {
            playership.resetDy();
        }
    }
    
    /**
     * Respond to mouse events
     * @param event
     **/
    public void mouseEvent(MouseEvent event) {
    }
    
    /**
     * Active us again
     **/
    public void gainedFocus() {
        active = true;
        //currentlevel.loopSound("theme");
    }
    
    /**
     * Deactive the timer
     **/
    public void lostFocus() {
        active = false;
        currentlevel.stopSound("theme");
    }
    
    /**
     * Sole purpose of this task is to increase the offset, so that
     * we advance on the level
     **/
    private class OffsetTask extends TimerTask {
        public void run() {
            if (currentlevel != null && active) {
                currentlevel.increaseOffset(1);
            }
        }
    }
    
}
