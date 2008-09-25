package projectuml;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * The game's level editor
 * 
 * @author Jens Thuresson, Steve Eriksson
 */
public class LevelEditor extends GameState {

    private Level level;
    private Font smallfont;
    private enum EditorCommandID
    {
        NEW, LOAD, SAVE, DELETE, CLEAR_ALL,
        TOGGLE_HELP, EXIT, SET_PLAYER_POSITION,
        PLACE_ENEMY, INCREASE_OFFSET, DECREASE_OFFSET
    };
    private boolean showhelp;
    private Hashtable<Integer, EditorCommandID> keys;

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
        smallfont = new Font("Courier New", Font.PLAIN, 12);
        showhelp = true;
        
        // Associate keybindings to specific
        // editor commands, of at least the size
        // of the enum structure
        keys = new Hashtable<Integer, EditorCommandID>(EditorCommandID.values().length);
        bindEditorKeys();
    }
    
    /**
     * Binds editor keys to specific keys
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
            g.setColor(Color.red);
            g.drawString(keyname, 0, y);
            g.setColor(Color.white);
            g.drawString(keys.get(i).toString(), 80, y);
            
            // Advance to next line
            y += smallfont.getSize();
        }
    }
    
    /**
     * TODO: do we need to update?
     * @param player
     */
    public void update(Player player) {
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
            // It's a valid key binding
            EditorCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case EXIT:
                    // TODO: check for "Do you want to save changes?"
                    removeMe = true;
                    break;
                    
                case CLEAR_ALL:
                    // TODO: ask "Really clear everything?"
                    level.removeAll();
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
                    
                default:
                    System.err.println("***  Unknown EditorCommandID: " + cmd + "  ***");
                    break;
            }
        }
    }

    public void mouseEvent(MouseEvent event) {
    }

    /** Not used **/
    public void gainedFocus() {
        
    }

    /** Not used **/
    public void lostFocus() {
        
    }
    
    /**
     * Saves the level (serialize it)
     * @param filename Where to store the level
     */
    public void save(String filename) {
        System.out.println("Trying to save level at " + filename);
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream outstream = new ObjectOutputStream(file);
            outstream.writeObject(level);
            outstream.close();
            file.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loads a level (deserialize it)
     * @param filename Where to load the level from
     */
    public void load(String filename) {
        System.out.println("Trying to load level from " + filename);
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream instream = new ObjectInputStream(file);
            level = (Level)instream.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
