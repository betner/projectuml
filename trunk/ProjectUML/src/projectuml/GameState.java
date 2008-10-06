package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * GameState
 * 
 * Represents a state in the game.
 *
 * @see GameStates
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class GameState {

    private GameStates gamestates;
    private Boolean removeMe = false;

    /**
     * Sets the GameStates-manager.
     *
     * @param states
     */
    public void setGameStateManager(GameStates states) {
        gamestates = states;
    }

    /**
     * Retrieves the gamestates-manager.
     * 
     * @return GameStates
     */
    public GameStates getGameStateManager() {
        return gamestates;
    }

    /**
     * Mark OK for removal. When the gamestate manager sees
     * this, it will pop the state from the statestack.
     */
    public void removeMe() {
        removeMe = true;
    }

    /**
     * Is it safe to remove this state?
     */
    public Boolean canRemove() {
        return removeMe;
    }

    /**
     * Called when the gamestate should update itself.
     * 
     * @param player playing the game
     */
    public abstract void update(Player player);

    /**
     * Called when the gamestate should redraw itself.
     * 
     * @param g2D Graphics context to draw to
     */
    public abstract void draw(Graphics2D g2D);

    /**
     * Called when the user presses or releases a key.
     * 
     * @param event The key event
     * @param down true if the key was pressed
     */
    public abstract void keyEvent(KeyEvent event, boolean down);

    /**
     * Called when the user does something with the mouse.
     * 
     * @param event mouse event
     */
    public abstract void mouseEvent(MouseEvent event);

    /**
     * Called when the gamestate gaines focus (e.g.
     * getting switched to by the gamestate manager).
     */
    public abstract void gainedFocus();

    /**
     * Called when the gamestate loses focus (e.g.
     * when it is removed from the view).
     */
    public abstract void lostFocus();
}
