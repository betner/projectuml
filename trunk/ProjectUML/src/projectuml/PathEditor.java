
package projectuml;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 * An editor for paths
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class PathEditor extends GameState {
    
    private Path path;
    private Font smallfont;
    private boolean showhelp;
    
    // Available editor commands
    private enum EditorCommandID {
        NEW, SAVE, LOAD,
        TOGGLE_HELP,
        EXIT
    };
    
    private Hashtable<Integer, EditorCommandID> keys;
    
    public PathEditor() {
        this(null);
    }
    
    /**
     * Creates the path editor
     * @param path
     **/
    public PathEditor(Path path) {
        this.path = path;
        keys = new Hashtable<Integer, EditorCommandID>();
        smallfont = new Font("Courier New", Font.PLAIN, 12);
        showhelp = true;

        bindKeys();
    }
    
    /**
     * Binds our editor keys
     ***/
    private void bindKeys() {
        keys.put(KeyEvent.VK_ESCAPE, EditorCommandID.EXIT);
        keys.put(KeyEvent.VK_N, EditorCommandID.NEW);
        keys.put(KeyEvent.VK_L, EditorCommandID.LOAD);
        keys.put(KeyEvent.VK_S, EditorCommandID.SAVE);
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
    }

    public void draw(Graphics2D g) {
        // Always draw a black background
        g.setColor(Color.black);
        g.fillRect(0, 0, 640, 480);
        
        if (showhelp) {
            showHelp(g);
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

    /** Not used **/
    public void gainedFocus() {
    }

    /** Not used **/
    public void lostFocus() {
    }

    public void mouseEvent(MouseEvent event) {
    }

    /**
     * Reponds to editor keys
     **/
    public void keyEvent(KeyEvent event) {
        if (keys.containsKey(event.getKeyCode())) {
            EditorCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case EXIT:
                    removeMe();
                    break;
                    
                case TOGGLE_HELP:
                    showhelp = !showhelp;
                    break;
                    
                default:
                    System.err.println("***  " + cmd.toString() + ", unknown editorcommand!  ***");
                    break;
            }
        }
    }

    public void update(Player player) {
    }
    

    
}
