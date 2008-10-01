package projectuml;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * The game's level editor, implemented using one
 * of the game's states for easier access
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
        SET_ENEMY_HEALTH,
        INCREASE_OFFSET, DECREASE_OFFSET,
        CHOOSE_SCENERY,
        START_PATH_EDITOR,
    };
    private boolean showhelp;
    private boolean update;
    private boolean unsavedchanges;
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
        unsavedchanges = false;
        
        // Level editor mode is ON by default
        if (level != null) {
            level.setEditorMode(true);
        }

        // FIX: how do we get the playership
        //      do we need it?
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
        keys.put(KeyEvent.VK_Z, EditorCommandID.SET_ENEMY_HEALTH);
    }

    /**
     * Helper function to print text. Will return the next line
     * to start printing on (the y-value)
     * @param text What to print
     * @param x Where to start printing on the x-axis
     * @return Y-value of next line to start on
     */
    private int println(Graphics2D g, String text, int x, int y) {
        g.drawString(text, x, y);
        return y + g.getFont().getSize();
    }

    /**
     * Display help text (key bindings)
     * @param g Graphics context to draw on
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
     * Displays a "Are you sure?"-dialog with
     * @param title Title of the dialog
     * @param text Question to ask
     * @return True if the user picked "Yes"
     */
    private boolean ask(String title, String text) {
        int opt = JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION);
        return opt == JOptionPane.YES_OPTION;
    }

    /**
     * Displays a input dialog, asking for an amount
     * (e.g. health)
     * @param title Title of the dialog
     * @param defaultvalue Default value to return if users cancels
     * @return The amount
     */
    private int askForValue(String title, int defaultvalue) {
        String opt = JOptionPane.showInputDialog(null, "Amount:", title, JOptionPane.PLAIN_MESSAGE);
        if (opt == null || opt.equals("")) {
            return defaultvalue;
        } else {
            return Integer.parseInt(opt);
        }
    }

    /**
     * Only updates if the user has turned it on
     * @param player
     */
    public void update(Player player) {
        if (update) {
            level.update(playership);
        }
    }

    /**
     * Paint the level
     * @param g Graphics context to draw on
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
     * @param event Key event
     */
    public void keyEvent(KeyEvent event, boolean down) {
        if (down && keys.containsKey(event.getKeyCode())) {
            // It's a valid key binding, check what
            // editor command it represents
            EditorCommandID cmd = keys.get(event.getKeyCode());
            switch (cmd) {
                case EXIT:
                    // If there's unsaved changes, ask the user
                    // if he wants to abort
                    if (unsavedchanges) {
                        if (!ask("Exit", "You have unsaved changes. Do you still want to proceed?")) {
                            break;
                        }
                    }
                    // Turn off level editor mode
                    if (level != null) {
                        level.setEditorMode(false);
                    }
                    removeMe();
                    break;

                case CLEAR_ALL:
                    // TODO: ask "Really clear everything?"
                    if (level != null) {
                        level.removeAll();
                    }
                    break;

                case NEW:
                    // If there's unsaved changes, ask the user
                    // if he wants to abort
                    if (unsavedchanges) {
                        if (!ask("New level", "You have unsaved changes. Do you still want to proceed?")) {
                            break;
                        }
                    }
                    // If there's no level active,
                    // create one. Otherwise just erase
                    // everything on the current one
                    if (level != null) {
                        level.removeAll();
                    } else {
                        level = new Level();
                        level.setEditorMode(showhelp);
                    }
                    unsavedchanges = false;
                    break;

                case TOGGLE_HELP:
                    // Doesn't only toggle help text but also
                    // level editor mode
                    showhelp = !showhelp;
                    if (level != null) {
                        level.setEditorMode(showhelp);
                    }
                    break;

                case TOGGLE_UPDATE:
                    // Enables updates on the level, i.e.
                    // we're "pretending" to run the level
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
                case SET_ENEMY_HEALTH:
                    // Just change the active mouse command
                    activecommand = cmd;
                    break;

                case SAVE: {
                    File path = browseForSave("Save level");
                    if (path != null) {
                        levelloader.save(level, path.getAbsolutePath());
                        unsavedchanges = false;
                    }
                    break;
                }

                case LOAD: {
                    File path = browse("Load level");
                    if (path != null) {
                        level = levelloader.load(path.getAbsolutePath());
                        level.setEditorMode(showhelp);
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

                case START_PATH_EDITOR: {
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
                case PLACE_ENEMY: {
                    // Create an enemy ship
                    //EnemyShip ship = new EnemyShip(new Path(false), "playership.png");
                    //ship.setPosition(event.getPoint());
                    Path path = new Path(true);
                    path.addPoint(new Point(0, 0));
                    EnemyShip ship = new EnemyShip(path, "enemyship1.png");
                    ship.setPosition(event.getPoint());
                    ship.show();
                    ship.activate();
                    level.addShip(ship);
                    unsavedchanges = true;
                    break;
                }

                case DELETE:
                    // Delete enemy at the position
                    level.removeShipAt(event.getPoint());
                    unsavedchanges = true;
                    break;

                case SET_PATH_ON_ENEMY: {
                    // Gives an enemy a pre-created path
                    EnemyShip ship = level.getShipAt(event.getPoint());
                    if (ship != null) {
                        File file = browse("Load path");
                        if (file != null) {
                            GeneralSerializer<Path> pathloader = new GeneralSerializer<Path>();
                            // TODO: see below, method isn't implemented
                            //ship.setPath(pathloader.load(file.getAbsoluteFile()));
                            System.err.println("***  Implement ship.setPath!!!  ***");
                            unsavedchanges = true;
                        }
                    }
                    break;
                }

                case SET_ENEMY_HEALTH: {
                    // Changes the health of an enemy
                    EnemyShip ship = level.getShipAt(event.getPoint());
                    if (ship != null) {
                        int amount = askForValue("New health", ship.getHealth());
                        // Since there's no setHealth...
                        ship.increaseHealth(amount - ship.getHealth());
                        unsavedchanges = true;
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