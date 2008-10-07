package projectuml;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GameRunning
 * 
 * The state that represents a running game.
 *
 * @see GameState
 * @author Jens Thuresson, Steve Eriksson
 */
public class GameRunning extends GameState {
    // Time value in milliseconds for an offset
    private final long OFFSET_PERIOD = 1000;
    private GeneralSerializer<Level> levelloader;
    private Level currentlevel;
    private Stack<String> levelnames;
    private PlayerShip playership;
    private Font font;

    private enum GameCommandID {

        GO_UP, GO_DOWN, GO_LEFT, GO_RIGHT,
        FIRE_WEAPON,
        PAUSE,
        START_LEVEL_EDITOR,
        TOGGLE_EDITOR_MODE,
        EXIT
    };
    private Hashtable<Integer, GameCommandID> keys;
    private Timer timer;
    private boolean active;

    /**
     * Creates the game and associates it with
     * a certain player.
     * 
     * @param player
     **/
    public GameRunning() {
        // Load a level
        levelloader = new GeneralSerializer<Level>();
        levelnames = new Stack<String>();
        initLevelList();
        loadNextLevel();

        // Get a font
        font = new Font("Courier New", Font.PLAIN, 10);

        // Create our timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new OffsetTask(), 0, OFFSET_PERIOD);
        active = true;

        keys = new Hashtable<Integer, GameCommandID>();
        bindKeys();
    }

    /**
     * Binds specific game keys to certain commands.
     */
    private void bindKeys() {
        keys.put(KeyEvent.VK_LEFT, GameCommandID.GO_LEFT);
        keys.put(KeyEvent.VK_RIGHT, GameCommandID.GO_RIGHT);
        keys.put(KeyEvent.VK_UP, GameCommandID.GO_UP);
        keys.put(KeyEvent.VK_DOWN, GameCommandID.GO_DOWN);
        keys.put(KeyEvent.VK_P, GameCommandID.PAUSE);
        keys.put(KeyEvent.VK_PAUSE, GameCommandID.PAUSE);
        keys.put(KeyEvent.VK_F12, GameCommandID.START_LEVEL_EDITOR);
        keys.put(KeyEvent.VK_SPACE, GameCommandID.FIRE_WEAPON);
        keys.put(KeyEvent.VK_H, GameCommandID.TOGGLE_EDITOR_MODE);
        keys.put(KeyEvent.VK_ESCAPE, GameCommandID.EXIT);
    }

    /**
     * Updates the world.
     * 
     * @param player
     */
    public void update(Player player) {
        // We haven't created our player ship
        // yet - do it!
        if (playership == null) {
            playership = new PlayerShip(player);
        }

        if (currentlevel != null) {
            currentlevel.update(playership);
            playership.update(currentlevel);
        }
        restrictPlayerShip();

        // Show game over-screen if we're dead and
        // destruction animation finished
        if (player.getLives() <= 0 && playership.getDestructAnimation().isDone()) {
            getGameStateManager().change(new GameOver());
            return;
        }

        // Level completed?
        if (currentlevel != null) {
            if (currentlevel.isCompleted()) {
                loadNextLevel();

                // If there are no more levels, we've completed
                // the game!
                if (currentlevel == null) {
                    getGameStateManager().change(new VictoryScreen());
                    return;
                }
            }
        }
    }

    /**
     * Repaints the running game.
     * 
     * @param g2D
     */
    public synchronized void draw(Graphics2D g2D) {
        if (currentlevel != null) {
            currentlevel.draw(g2D);
        }
        if (playership != null) {
            playership.draw(g2D);
        }

        if (currentlevel != null && currentlevel.inEditorMode()) {
            // Print out debug information
            g2D.setFont(font);
            g2D.setColor(Color.white);
            g2D.drawString("Offset: " + currentlevel.getOffset(), 0, 460);
        }
    }

    /**
     * Respond to key events, most importantly moving
     * the player ship.
     * 
     * @param event
     */
    public void keyEvent(KeyEvent event, boolean down) {
        // We may not have a valid playership yet
        if (playership == null) {
            return;
        }
        
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
                    if (!down) {
                        playership.fire(currentlevel);
                    }
                    break;

                case TOGGLE_EDITOR_MODE:
                    if (currentlevel != null && !down) {
                        currentlevel.setEditorMode(!currentlevel.inEditorMode());
                    }
                    break;

                case PAUSE:
                    if (down) {
                        getGameStateManager().push(new Pause());
                    }
                    break;

                case START_LEVEL_EDITOR:
                    if (down) {
                        getGameStateManager().push(new LevelEditor(currentlevel));
                    }
                    break;

                case EXIT:
                    getGameStateManager().change(new MainMenu());
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Ensures the player ship isn't going off-screen.
     */
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
     * Respond to mouse events.
     * 
     * @param event
     */
    public void mouseEvent(MouseEvent event) {
    }

    /**
     * Activate us again.
     */
    public void gainedFocus() {
        active = true;
        if (currentlevel != null) {
            currentlevel.loopSound("theme");
        }
    }

    /**
     * Deactive the timer.
     */
    public void lostFocus() {
        active = false;
        if (currentlevel != null) {
            currentlevel.stopSound("theme");
        }
    }

    /**
     * Scans the current directory for
     * .level-files, and stores their names in
     * levelnames.
     */
    private void initLevelList() {
        levelnames.clear();
        File dir = new File(".");
        for (String filename : dir.list(new JustLevels())) {
            // Push it first, since we're jusing a stack
            levelnames.add(0, filename);
            System.out.println("Found level: " + filename);
        }
        if (levelnames.isEmpty()) {
            System.err.println("Not a single level was found!");
        }
    }

    /**
     * Used to get all the .level-files.
     */
    private class JustLevels implements FilenameFilter {

        public boolean accept(File dir, String name) {
            return (name.endsWith(".level"));
        }
    }

    /**
     * Loads the next level. If there are no more levels to
     * load, it will return null.
     */
    private void loadNextLevel() {
        lostFocus();
        if (levelnames.empty()) {
            currentlevel = null;
        } else {
            String filename = levelnames.pop();
            System.out.println("Loading level " + filename + "...");
            currentlevel = levelloader.load(filename);
        }
        gainedFocus();
    }

    /**
     * Sole purpose of this task is to increase the offset, so that
     * we advance on the level.
     */
    private class OffsetTask extends TimerTask {

        public void run() {
            if (active && currentlevel != null) {
                currentlevel.increaseOffset(1);
            }
        }
    }
}
