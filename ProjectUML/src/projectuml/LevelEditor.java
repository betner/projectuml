package projectuml;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * The game's level editor
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class LevelEditor extends GameState {
    
    private GeneralSerializer<Level> levelloader;
    private Level level;
    private Font smallfont;
    private PlayerShip playership;
    
    // Different commands the editor recognizes
    private enum EditorCommandID {
        DO_NOTHING,
        NEW, LOAD, SAVE,
        DELETE, CLEAR_ALL,
        TOGGLE_HELP, TOGGLE_UPDATE,
        EXIT,
        PLACE_ENEMY, SET_PATH_ON_ENEMY,
        INCREASE_OFFSET, DECREASE_OFFSET,
        CHOOSE_SCENERY,
        START_PATH_EDITOR,
    };
    
    private boolean showhelp;
    private boolean update;
    private Hashtable<Integer, EditorCommandID> keys;
    private EditorCommandID activecommand;
    
    /**
     * Starts the level editor
     */
    public LevelEditor() {
        this(null);
    }
    
    /**
     * Starts editing a certain level
     * @param level Level to edit
     */
    public LevelEditor(Level level) {
        this.level = level;
        levelloader = new GeneralSerializer<Level>();
        smallfont = new Font("Courier New", Font.PLAIN, 12);
        showhelp = true;
        update = false;
        
        // FIX: how do we get the playership
        playership = new PlayerShip(new Player());
        
        // Associate keybindings to specific
        // editor commands, of at least the size
        // of the enum structure
        keys = new Hashtable<Integer, EditorCommandID>();
        bindEditorKeys();
        
        // The active command to execute when we click
        // the mouse button. By default, do nothing
        activecommand = EditorCommandID.DO_NOTHING;
    }
    
    /**
     * Binds editor commands to specific keys
     */
    private void bindEditorKeys() {
        keys.put(KeyEvent.VK_N, EditorCommandID.NEW);
        keys.put(KeyEvent.VK_L, EditorCommandID.LOAD);
        keys.put(KeyEvent.VK_S, EditorCommandID.SAVE);
        keys.put(KeyEvent.VK_DELETE, EditorCommandID.DELETE);
        keys.put(KeyEvent.VK_F9, EditorCommandID.CLEAR_ALL);
        keys.put(KeyEvent.VK_F12, EditorCommandID.EXIT);
        keys.put(KeyEvent.VK_H, EditorCommandID.TOGGLE_HELP);
        keys.put(KeyEvent.VK_ADD, EditorCommandID.INCREASE_OFFSET);
        keys.put(KeyEvent.VK_SUBTRACT, EditorCommandID.DECREASE_OFFSET);
        keys.put(KeyEvent.VK_SPACE, EditorCommandID.PLACE_ENEMY);
        keys.put(KeyEvent.VK_F1, EditorCommandID.CHOOSE_SCENERY);
        keys.put(KeyEvent.VK_U, EditorCommandID.TOGGLE_UPDATE);
        keys.put(KeyEvent.VK_F2, EditorCommandID.START_PATH_EDITOR);
        keys.put(KeyEvent.VK_P, EditorCommandID.SET_PATH_ON_ENEMY);
    }
    
    /**
     * Helper function to print text. Will return the next line
     * to start printing on (the y-value)
     * @param text
     * @param x
     * @return Y-value of next line to start on
     */
    private int println(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
        return y + g.getFont().getSize();
    }
    
    /**
     * Display help text (key bindings)
     */
    private void showHelp(Graphics2D g) {
        // Where to start displaying
        int y = smallfont.getSize();
        g.setFont(smallfont);
        for (Integer i : keys.keySet()) {
            String keyname = KeyEvent.getKeyText(i.intValue());
            g.setColor(Color.green);
            println(g, keyname, 0, y);
            g.setColor(Color.white);
            y = println(g, keys.get(i).toString(), 80, y);
        }
        
        // Display other information two rows below
        y += smallfont.getSize();
        if (update) {
            y = println(g, "Updating is ON", 0, y);
        }
        
        // Display offset at bottom
        if (level != null) {
            g.setColor(Color.white);
            g.drawString("Level offset:   " + level.getOffset(), 0, 480 - smallfont.getSize());
            g.drawString("Active command: " + activecommand.toString(), 0, 480);
        }
    }
    
    /**
     * Browse for a filename
     * @param title Title of the dialog
     * @return The file object, or null
     */
    private File browse(String title) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle(title);
        if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return filechooser.getSelectedFile();
        } else {
            return null;
        }
    }
    
    /**
     * Browse for a file to save information to
     * @param title Title of the dialog
     * @return The file object, or null
     */
    private File browseForSave(String title) {
        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle(title);
        if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            return filechooser.getSelectedFile();
        } else {
            return null;
        }
    }
    
    /**
     * Only updates if the user has turned it on
     * @param player
     */
    public void update(Player player) {
        if (update) {
            // FIX: row below
            level.update(playership);
        }
    }
    
    /**
     * Paint the level
     * @param g
     */
    public void draw(Graphics2D g) {
        // Always draw a black background
        g.setColor(Color.black);
        g.fillRect(0, 0, 640, 480);
        
        // Draw the level
        if (level != null) {
            level.draw(g);
        } else {
            g.setColor(Color.red);
            g.setFont(smallfont);
            g.drawString("***  No active level, please create a new  ***", 170, 220);
        }
        
        // Display help (if active)
        if (showhelp) {
            showHelp(g);
        }
    }
    
    /**
     * Respons to key press
     * @param event
     */
    public void keyEvent(KeyEvent event) {
        if (keys.containsKey(event.getKeyCode())) {
            // It's a valid key binding, check what
            // editor command it represents
            EditorCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case EXIT:
                    // TODO: check for "Do you want to save changes?"
                    removeMe();
                    break;
                    
                case CLEAR_ALL:
                    // TODO: ask "Really clear everything?"
                    if (level != null) {
                        level.removeAll();
                    }
                    break;
                    
                case NEW:
                    // TODO: see EXIT
                    if (level != null) {
                        level.removeAll();
                    } else {
                        level = new Level();
                    }
                    break;
                    
                case TOGGLE_HELP:
                    showhelp = !showhelp;
                    break;
                    
                case TOGGLE_UPDATE:
                    update = !update;
                    break;
                    
                case INCREASE_OFFSET:
                    if (level != null) {
                        level.increaseOffset(1);
                    }
                    break;
                    
                case DECREASE_OFFSET:
                    if (level != null) {
                        level.decreaseOffset(1);
                    }
                    break;
                    
                case DO_NOTHING:
                case DELETE:
                case PLACE_ENEMY:
                case SET_PATH_ON_ENEMY:
                    // Just change the active mouse command
                    activecommand = cmd;
                    break;
                    
                case SAVE: {
                    File path = browseForSave("Save level");
                    if (path != null) {
                        //save(path.getAbsolutePath());
                        levelloader.save(level, path.getAbsolutePath());
                    }
                    break;
                }
                
                case LOAD: {
                    File path = browse("Load level");
                    if (path != null) {
                        //load(path.getAbsolutePath());
                        level = levelloader.load(path.getAbsolutePath());
                    }
                    break;
                }
                
                case CHOOSE_SCENERY: {
                    // Browse for a already saved scenery
                    if (level != null) {
                        File path = browse("Load scenery");
                        if (path != null) {
                            GeneralSerializer<Scenery> loader = new GeneralSerializer<Scenery>();
                            Scenery scenery = loader.load(path.getAbsolutePath());
                            // Did it get loaded?
                            if (scenery != null) {
                                level.setScenery(scenery);
                            }
                        }
                    }
                    break;
                }
                
                case START_PATH_EDITOR:
                {
                    // Switch to the path editor
                    getGameStateManager().push(new PathEditor());
                    break;
                }
                
                default:
                    // The current editor command id isn't implemented
                    System.err.println("***  Unknown EditorCommandID: " + cmd + "  ***");
                    break;
            }
        }
    }
    
    /**
     * Respond to the active command
     * @param event
     */
    public void mouseEvent(MouseEvent event) {
        if (level != null) {
            switch (activecommand) {
                case PLACE_ENEMY:
                {
                    EnemyShip ship = new EnemyShip();
                    ship.setPosition(event.getPoint());
                    ship.loadImageFrom("playership.png");
                    ship.show();
                    ship.activate();
                    level.addShip(ship);
                    break;
                }
                    
                case DELETE:
                    // Delete enemy at the position
                    level.removeShipAt(event.getPoint());
                    break;
                    
                case SET_PATH_ON_ENEMY:
                {
                    // Gives an enemy a pre-created path
                    EnemyShip ship = level.getShipAt(event.getPoint());
                    if (ship != null) {
                        File file = browse("Load path");
                        if (file != null) {
                            GeneralSerializer<Path> pathloader = new GeneralSerializer<Path>();
                            // TODO: see below, method isn't implemented
                            //ship.setPath(pathloader.load(file.getAbsoluteFile()));
                        }
                    }
                    break;
                }
                
                default:
                    break;
            }
        }
    }
    
    /** Not used **/
    public void gainedFocus() {
    }
    
    /** Not used **/
    public void lostFocus() {
    }
    
    /**
     * Gets the level object we've been working
     * on
     * @return Active level, or null
     */
    public Level getLevel() {
        return level;
    }
}
