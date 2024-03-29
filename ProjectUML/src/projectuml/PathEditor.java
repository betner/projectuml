package projectuml;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;

/**
 * PathEditor
 * 
 * An editor for paths.
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public class PathEditor extends GameState {

    private Path path;
    private Font smallfont;
    private boolean showhelp;
    private GeneralSerializer<Path> pathloader;
    // Available editor commands
    private enum EditorCommandID {

        NEW, SAVE, LOAD,
        TOGGLE_HELP, TOGGLE_CYCLIC,
        CLEAR_ALL,
        MOVE_PATH_UP, MOVE_PATH_DOWN,
        MOVE_PATH_RIGHT, MOVE_PATH_LEFT,
        EXIT
    };
    private Hashtable<Integer, EditorCommandID> keys;

    /**
     * Creates new instance of PathEditor.
     */
    public PathEditor() {
        this(null);
    }

    /**
     * Creates new instance of PathEditor.
     * 
     * @param path
     */
    public PathEditor(Path path) {
        this.path = path;
        keys = new Hashtable<Integer, EditorCommandID>();
        smallfont = new Font("Courier New", Font.PLAIN, 12);
        showhelp = true;
        pathloader = new GeneralSerializer<Path>();
        bindKeys();
    }

    /**
     * Binds our editor keys.
     */
    private void bindKeys() {
        keys.put(KeyEvent.VK_ESCAPE, EditorCommandID.EXIT);
        keys.put(KeyEvent.VK_N, EditorCommandID.NEW);
        keys.put(KeyEvent.VK_L, EditorCommandID.LOAD);
        keys.put(KeyEvent.VK_S, EditorCommandID.SAVE);
        keys.put(KeyEvent.VK_C, EditorCommandID.TOGGLE_CYCLIC);
        keys.put(KeyEvent.VK_H, EditorCommandID.TOGGLE_HELP);
        keys.put(KeyEvent.VK_DELETE, EditorCommandID.CLEAR_ALL);
        keys.put(KeyEvent.VK_UP, EditorCommandID.MOVE_PATH_UP);
        keys.put(KeyEvent.VK_DOWN, EditorCommandID.MOVE_PATH_DOWN);
        keys.put(KeyEvent.VK_LEFT, EditorCommandID.MOVE_PATH_LEFT);
        keys.put(KeyEvent.VK_RIGHT, EditorCommandID.MOVE_PATH_RIGHT);
    }

    /**
     * Helper function to print text. Will return the next line
     * to start printing on (the y-value).
     * 
     * @param text
     * @param x
     * @return Y-value of next line to start on
     */
    private int println(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
        return y + g.getFont().getSize();
    }

    /**
     * Display help text (key bindings).
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

        if (path != null && path.isCyclic()) {
            g.setColor(Color.magenta);
            y = println(g, "Path is cyclic", 0, y);
            y += smallfont.getSize();
        }

        // General help commands
        g.setColor(Color.white);
        y = println(g, "Left mouse button adds point", 0, y);
        y = println(g, "Right mouse button removes latest point added", 0, y);
        y += smallfont.getSize();
        y = println(g, "Use arrow keys to move path as a whole", 0, y);
    }

    /**
     * Repaints the path editor.
     * 
     * @param g2D Graphics2D
     */
    public void draw(Graphics2D g2D) {
        // Always draw a black background
        g2D.setColor(Color.black);
        g2D.fillRect(0, 0, 640, 480);

        if (showhelp) {
            showHelp(g2D);
        }

        // No path loaded/created?
        if (path == null) {
            g2D.setFont(smallfont);
            g2D.setColor(Color.red);
            g2D.drawString("***  No active path, please create a new  ***", 170, 220);
        } else {
            drawPath(g2D);
        }
    }

    /**
     * Browse for a filename.
     * 
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
     * Browse for a file to save information to.
     * 
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

    /**
     * Respond to mouse events.
     * 
     * @param event
     */
    public void mouseEvent(MouseEvent event) {
        // Mouse operations only works if
        // there's an active path
        if (path == null) {
            return;
        }

        switch (event.getButton()) {
            case MouseEvent.BUTTON1:
                // Left mousebutton
                path.addPoint(event.getPoint());
                break;

            case MouseEvent.BUTTON2:
                // Middle mousebutton
                break;

            case MouseEvent.BUTTON3:
                // Right mousebutton
                //path.removeAt(event.getPoint());
                path.removeLast();
                break;

            default:
                break;
        }
    }

    /**
     * Draw our path (if we have any).
     */
    private void drawPath(Graphics2D g) {
        if (path != null) {
            // Turn off any cyclic behaviour so that
            // we can draw properly
            boolean cyclic = path.isCyclic();
            path.setCyclic(false);
            path.reset();
            Point lastpoint = null;
            while (true) {
                Point point = path.next();
                if (point == null) {
                    break;
                } else {
                    g.setColor(Color.green);
                    g.fillOval((int) point.getX() - 3, (int) point.getY() - 3, 6, 6);
                    // Did we have a previous one?
                    if (lastpoint != null) {
                        g.setColor(Color.gray);
                        g.drawLine((int) lastpoint.getX(), (int) lastpoint.getY(),
                                (int) point.getX(), (int) point.getY());
                    }
                }
                lastpoint = point;
            }
            // Restore original cyclic behaviour
            path.setCyclic(cyclic);
        }
    }

    /**
     * Repond to editor keys.
     */
    public void keyEvent(KeyEvent event, boolean down) {
        // How fast we should move the entire
        // path
        final int MOVE_SPEED = 5;
        
        // Is it a command key?
        if (down && keys.containsKey(event.getKeyCode())) {
            EditorCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case EXIT:
                    removeMe();
                    break;

                case TOGGLE_HELP:
                    showhelp = !showhelp;
                    break;

                case TOGGLE_CYCLIC:
                    if (path != null) {
                        path.setCyclic(!path.isCyclic());
                    }
                    break;

                case NEW:
                    path = new Path(false);
                    break;

                case SAVE: {
                    if (path != null) {
                        File file = browseForSave("Save path");
                        if (file != null) {
                            pathloader.save(path, file.getAbsolutePath());
                        }
                    }
                    break;
                }

                case LOAD: {
                    File file = browse("Load path");
                    if (file != null) {
                        path = pathloader.load(file.getAbsolutePath());
                    }
                    break;
                }

                case CLEAR_ALL:
                    if (path != null) {
                        path.removeAll();
                    }
                    break;

                case MOVE_PATH_UP:
                    if (path != null) {
                        path.translate(0, -MOVE_SPEED);
                    }
                    break;

                case MOVE_PATH_DOWN:
                    if (path != null) {
                        path.translate(0, MOVE_SPEED);
                    }
                    break;

                case MOVE_PATH_RIGHT:
                    if (path != null) {
                        path.translate(MOVE_SPEED, 0);
                    }
                    break;

                case MOVE_PATH_LEFT:
                    if (path != null) {
                        path.translate(-MOVE_SPEED, 0);
                    }
                    break;

                default:
                    System.err.println("***  " + cmd.toString() + ", not implemented!  ***");
                    break;
            }
        }
    }

    /** Not used here **/
    public void update(Player player) {
    }
}
