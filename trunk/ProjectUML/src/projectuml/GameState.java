package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Represents a state in the game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class GameState {
    
    protected GameStates gamestates;
    protected Boolean removeMe = false;
    
    /**
     * Sets the GameStates-manager
     *
     * @param states
     **/
    public void setManager(GameStates states) {
        gamestates = states;
    }
    
    /**
     * Is it safe to remove this state?
     **/
    public Boolean canRemove() {
        return removeMe;
    }
    
    public abstract void update(Player player);
    public abstract void draw(Graphics2D g);
    public abstract void keyEvent(KeyEvent event);
    public abstract void mouseEvent(MouseEvent event);
    public abstract void gainedFocus();
    public abstract void lostFocus();
    
}
