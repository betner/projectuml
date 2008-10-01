package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Represents a state in the game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class GameState {
    
    private GameStates gamestates;
    private Boolean removeMe = false;
    
    /**
     * Sets the GameStates-manager
     *
     * @param states
     **/
    public void setGameStateManager(GameStates states) {
        gamestates = states;
    }
    
    /**
     * Retrieves the gamestates-manager
     * @return GameStates
     **/
    public GameStates getGameStateManager() {
        return gamestates;
    }

    /**
     * Mark OK for removal
     **/
    public void removeMe() {
        removeMe = true;
    }
    
    /**
     * Is it safe to remove this state?
     **/
    public Boolean canRemove() {
        return removeMe;
    }
    
    public abstract void update(Player player);
    public abstract void draw(Graphics2D g);
    public abstract void keyEvent(KeyEvent event, boolean down);
    public abstract void mouseEvent(MouseEvent event);
    public abstract void gainedFocus();
    public abstract void lostFocus();
    
}
