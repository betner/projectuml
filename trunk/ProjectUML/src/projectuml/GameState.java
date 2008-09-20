package projectuml;

import java.awt.*;
import java.awt.event.*;

/**
 * Represents a state in the game
 *
 * @author Jens Thuresson, Steve Eriksson
 */
public abstract class GameState {
    
    public abstract void update(Player player, GameStates gamestates);
    public abstract void draw(Graphics2D g);
    public abstract void keyEvent(KeyEvent event);
    public abstract void mouseEvent(MouseEvent event);
    
}
